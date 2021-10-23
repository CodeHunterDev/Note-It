package com.riyazuddin.noteit.data.repository

import android.content.Context
import com.riyazuddin.noteit.common.checkForInternetConnection
import com.riyazuddin.noteit.common.networkBoundResource
import com.riyazuddin.noteit.data.local.NoteDao
import com.riyazuddin.noteit.data.model.LocallyDeletedNote
import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.data.remote.NoteItApi
import com.riyazuddin.noteit.data.remote.request.DeleteNoteRequest
import com.riyazuddin.noteit.domain.repository.INoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class NoteRepositoryImp @Inject constructor(
    private val noteDao: NoteDao,
    private val noteItApi: NoteItApi,
    private val context: Context
) : INoteRepository {

    override suspend fun insertNote(note: Note) {
        noteDao.insert(note)
        sendInsertNoteToApi(note)
    }


    private suspend fun sendInsertNoteToApi(note: Note) {
        withContext(Dispatchers.IO + NonCancellable) {
            val response = try {
                noteItApi.addNote(note)
            } catch (e: Exception) {
                null
            }
            if (response != null && response.isSuccessful) {
                noteDao.insert(note.apply { isSynced = true })
            } else
                noteDao.insert(note)
        }
    }

    override suspend fun insertNotes(notes: List<Note>) {
        notes.forEach { insertNote(it) }
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNoteByID(note.id)
        noteDao.insertLocallyDeletedNoteId(LocallyDeletedNote(note.id))
        sendDeleteNoteToApi(note.id)
    }

    private suspend fun sendDeleteNoteToApi(noteId: String) {
        withContext(Dispatchers.IO + NonCancellable) {
            val request = DeleteNoteRequest(noteId)
            val response = try {
                noteItApi.deleteNote(request)
            } catch (e: Exception) {
                null
            }
            if (response != null && response.isSuccessful) {
                noteDao.deletedLocallyDeletedNote(noteId)
            }
        }
    }

    override suspend fun getNote(notedId: String): Note? {
        return noteDao.getNote(notedId)
    }

    override suspend fun syncNotes(): Response<List<Note>> {

        val locallyDeletedNotes = noteDao.getAllLocallyDeleteNotes()
        locallyDeletedNotes.forEach {
            sendDeleteNoteToApi(it.locallyDeletedNoteId)
        }

        val unSyncedNotes = noteDao.getAllUnSyncedNotes()
        unSyncedNotes.forEach {
            sendInsertNoteToApi(it)
        }
        return noteItApi.getNotes()
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return networkBoundResource(
            query = {
                noteDao.getAllNotes()
            },
            fetch = {
                syncNotes()
            },
            saveFetchResult = { response ->
                noteDao.deleteAllNotes()
                response.body()?.let { notes ->
                    notes.onEach { note ->
                        note.isSynced = true
                        noteDao.insert(note)
                        Timber.i("$note")
                    }
                }
            },
            shouldFetch = {
                checkForInternetConnection(context)
            }
        )
    }
}