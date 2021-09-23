package com.riyazuddin.noteit.data.repository

import android.content.Context
import com.riyazuddin.noteit.common.Resource
import com.riyazuddin.noteit.common.checkForInternetConnection
import com.riyazuddin.noteit.common.networkBoundResource
import com.riyazuddin.noteit.data.local.NoteDao
import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.data.remote.NoteItApi
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
        sendNoteToApi(note)
    }

    private suspend fun sendNoteToApi(note: Note){
        withContext(Dispatchers.IO + NonCancellable){
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
    }

    override suspend fun getNote(notedId: String): Note? {
        return noteDao.getNote(notedId)
    }

    override suspend fun syncNotes(): Response<List<Note>> {
        val unSyncedNotes = noteDao.getAllUnSyncedNotes()
        return if (unSyncedNotes.isEmpty()){
            Timber.i("EMPTY LIST")
            Response.success(emptyList())
        }
        else{
            Timber.i("NOT EMPTY LIST")
            unSyncedNotes.forEach {
                sendNoteToApi(it)
            }
            noteItApi.getNotes()
        }

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
                response.body()?.let { notes ->
                    notes.onEach { note ->
                        note.isSynced = true
                        noteDao.insert(note)
                    }
                }
            },
            shouldFetch = {
                checkForInternetConnection(context)
            }
        )
    }
}