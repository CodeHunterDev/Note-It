package com.riyazuddin.noteit.domain.repository

import com.riyazuddin.noteit.common.Resource
import com.riyazuddin.noteit.data.model.Note
import kotlinx.coroutines.flow.Flow

interface INoteRepository {

    suspend fun insertNote(note: Note)

    suspend fun insertNotes(notes: List<Note>)

    fun getAllNotes(): Flow<Resource<List<Note>>>

    suspend fun syncNotes()
}