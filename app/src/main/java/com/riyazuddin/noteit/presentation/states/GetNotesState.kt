package com.riyazuddin.noteit.presentation.states

import com.riyazuddin.noteit.data.model.Note

data class GetNotesState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val notes: List<Note> = emptyList(),
    val error: String = ""
)
