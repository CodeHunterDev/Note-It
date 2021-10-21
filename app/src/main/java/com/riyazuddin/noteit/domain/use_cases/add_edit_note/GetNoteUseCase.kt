package com.riyazuddin.noteit.domain.use_cases.add_edit_note

import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.domain.repository.INoteRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val noteRepository: INoteRepository
) {
    suspend operator fun invoke(noteId: String): Note? {
        return noteRepository.getNote(noteId)
    }
}