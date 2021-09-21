package com.riyazuddin.noteit.data.repository

import android.content.Context
import com.riyazuddin.noteit.common.Resource
import com.riyazuddin.noteit.common.checkForInternetConnection
import com.riyazuddin.noteit.common.networkBoundResource
import com.riyazuddin.noteit.data.local.NoteDao
import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.data.remote.NoteItApi
import com.riyazuddin.noteit.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class NotesRepositoryImp @Inject constructor(
    private val noteDao: NoteDao,
    private val noteItApi: NoteItApi,
    private val context: Context
) : INoteRepository {

    override suspend fun insertNote(note: Note) {
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

    override suspend fun insertNotes(notes: List<Note>) {
        notes.forEach { insertNote(it) }
    }

    private var curNotesResponse: Response<List<Note>>? = null

    override suspend fun syncNotes() {
        val unSyncedNotes = noteDao.getAllUnSyncedNotes()
        unSyncedNotes.forEach {
            insertNote(it)
        }

        curNotesResponse = noteItApi.getNotes()
//        curNotesResponse?.body()?.let { notes ->
//            noteDao.deleteAllNotes()
//            notes.onEach {
//                it.isSynced = true
//                noteDao.insert(it)
//            }
//        }
    }

    override fun getAllNotes(): Flow<Resource<List<Note>>> {
        return networkBoundResource(
            query = {
                noteDao.getAllNotes()
            },
            fetch = {
                syncNotes()
                curNotesResponse
            },
            saveFetchResult = { response ->
                response?.body()?.let { notes ->
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