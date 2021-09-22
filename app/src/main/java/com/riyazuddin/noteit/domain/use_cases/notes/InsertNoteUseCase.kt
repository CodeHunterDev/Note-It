package com.riyazuddin.noteit.domain.use_cases.notes

import com.riyazuddin.noteit.data.model.InvalidNoteException
import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.domain.repository.INoteRepository
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(
    private val noteRepository: INoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if (note.title.isEmpty())
            throw InvalidNoteException("Title cannot be empty.")
        noteRepository.insertNote(note)
    }
}