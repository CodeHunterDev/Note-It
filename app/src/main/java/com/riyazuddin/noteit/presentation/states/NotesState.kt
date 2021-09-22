package com.riyazuddin.noteit.presentation.states

import com.riyazuddin.noteit.common.NoteOrder
import com.riyazuddin.noteit.common.OrderType
import com.riyazuddin.noteit.data.model.Note

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Ascending),
    val isOrderSelectionVisible: Boolean = false
)
