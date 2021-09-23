package com.riyazuddin.noteit.domain.repository

import com.riyazuddin.noteit.data.model.Note
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface INoteRepository {

    suspend fun insertNote(note: Note)

    suspend fun insertNotes(notes: List<Note>)

    suspend fun deleteNote(note: Note)

    suspend fun getNote(notedId: String): Note?

    fun getAllNotes(): Flow<List<Note>>

    suspend fun syncNotes(): Response<List<Note>>
}