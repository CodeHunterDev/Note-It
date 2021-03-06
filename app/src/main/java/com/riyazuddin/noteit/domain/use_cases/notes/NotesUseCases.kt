package com.riyazuddin.noteit.domain.use_cases.notes

import com.riyazuddin.noteit.domain.use_cases.add_edit_note.GetNoteUseCase

data class NotesUseCases(
    val getNotes: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val insertNoteUseCase: InsertNoteUseCase,
    val getNote: GetNoteUseCase
)