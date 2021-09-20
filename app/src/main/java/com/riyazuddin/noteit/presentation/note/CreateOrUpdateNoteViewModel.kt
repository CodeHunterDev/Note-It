package com.riyazuddin.noteit.presentation.note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.riyazuddin.noteit.data.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateOrUpdateNoteViewModel @Inject constructor(

) : ViewModel() {

    fun setNote(note: Note) {
        _id.value = note.id
        _title.value = note.title
        _content.value = note.content
        _date.value = note.date
        _owner.value = note.owner
        _color.value = note.color
    }

    private val _id = mutableStateOf("")
    val id: State<String> = _id
    fun setId(id: String) {
        _id.value = id
    }

    private val _title = mutableStateOf("")
    val title: State<String> = _title
    fun setTitle(title: String) {
        _title.value = title
    }

    private val _content = mutableStateOf("")
    val content: State<String> = _content
    fun setContent(content: String) {
        _content.value = content
    }

    private val _date = mutableStateOf(0L)
    val date: State<Long> = _date
    fun setDate(date: Long) {
        _date.value = date
    }

    private val _owner = mutableStateOf("")
    val owner: State<String> = _owner
    fun setOwner(owner: String) {
        _owner.value = owner
    }

    private val _color = mutableStateOf("")
    val color: State<String> = _color
    fun setColor(color: String) {
        _color.value = color
    }
    
}