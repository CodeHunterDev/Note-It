package com.riyazuddin.noteit.common

sealed class UIEvent {
    data class ShowSnackbar(val message: String) : UIEvent()
    object OnSuccessEvent : UIEvent()
}