package com.riyazuddin.noteit.domain.use_cases.notes

import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.domain.repository.INoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val noteRepository: INoteRepository
) {

    suspend operator fun invoke(note: Note){
        noteRepository.deleteNote(note)
    }
}