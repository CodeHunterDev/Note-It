package com.riyazuddin.noteit.presentation.states

data class NoteState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val successMessage: String = "",
    val error: String = ""
)