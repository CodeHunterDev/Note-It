package com.riyazuddin.noteit.domain.use_cases.create_or_update_note

import com.riyazuddin.noteit.common.Resource
import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateOrUpdateNoteUseCase @Inject constructor(
    private val noteRepository: INoteRepository
) {

    suspend operator fun invoke(
        id: String,
        title: String,
        content: String,
        date: Long,
        owner: String,
        color: Int
    ) {
        noteRepository.insertNote(Note(id, title, content, date, owner, color))
    }
}