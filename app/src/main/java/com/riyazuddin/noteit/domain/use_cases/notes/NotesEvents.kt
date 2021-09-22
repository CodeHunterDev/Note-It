package com.riyazuddin.noteit.domain.use_cases.notes

import com.riyazuddin.noteit.common.NoteOrder
import com.riyazuddin.noteit.data.model.Note

sealed class NotesEvents {
    data class Order(val noteOrder: NoteOrder) : NotesEvents()
    data class DeleteNote(val note: Note) : NotesEvents()
    object RestoreRecentlyDeletedNote : NotesEvents()
    object ToggleOrderSection : NotesEvents()
}
