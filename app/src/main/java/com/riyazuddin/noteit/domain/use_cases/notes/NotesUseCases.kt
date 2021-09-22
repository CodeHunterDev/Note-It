package com.riyazuddin.noteit.domain.use_cases.notes

data class NotesUseCases(
    val getNotes: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val insertNoteUseCase: InsertNoteUseCase
)