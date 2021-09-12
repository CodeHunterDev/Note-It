package com.riyazuddin.noteit.presentation.note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(): ViewModel(){

    private val _title = mutableStateOf("")
    val title: State<String> = _title
    fun setTitle(title: String){
        _title.value = title
    }

    private val _subTitle = mutableStateOf("")
    val subTitle: State<String> = _subTitle
    fun setSubTitle(subTitle: String){
        _subTitle.value = subTitle
    }

    private val _content = mutableStateOf("")
    val content: State<String> = _content
    fun setContent(content: String){
        _content.value = content
    }
}