package com.riyazuddin.noteit.domain.use_cases.get_notes

import com.riyazuddin.noteit.common.Resource
import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val noteRepository: INoteRepository
) {

    operator fun invoke(): Flow<Resource<List<Note>>> = flow {
        emit(Resource.Loading())
        val a = noteRepository.getAllNotes()
        emitAll(a)
    }
}