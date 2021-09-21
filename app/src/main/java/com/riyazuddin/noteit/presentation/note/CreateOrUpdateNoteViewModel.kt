package com.riyazuddin.noteit.presentation.note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.riyazuddin.noteit.common.Resource
import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.domain.use_cases.create_or_update_note.CreateOrUpdateNoteUseCase
import com.riyazuddin.noteit.presentation.states.NoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateOrUpdateNoteViewModel @Inject constructor(
    private val createOrUpdateNote: CreateOrUpdateNoteUseCase
) : ViewModel() {

    fun setNote(note: Note) {
        setId(note.id)
        setTitle(note.title)
        setContent(note.content)
        setDate(note.date)
        setOwner(note.owner)
        setColor(note.color)
    }

    private val _id = mutableStateOf("")
    val id: State<String> = _id
    private fun setId(id: String) {
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
    private fun setDate(date: Long) {
        _date.value = date
    }

    private val _owner = mutableStateOf("")
    private val owner: State<String> = _owner
    private fun setOwner(owner: String) {
        _owner.value = owner
    }

    private val _color = mutableStateOf("")
    val color: State<String> = _color
    fun setColor(color: String) {
        _color.value = color
    }

    private val _noteState = mutableStateOf(NoteState())
    val noteState: State<NoteState> = _noteState
    @DelicateCoroutinesApi
    fun saveNote() {
        createOrUpdateNote(
            id.value,
            title.value,
            content.value,
            date.value,
            owner.value,
            color.value
        ).onEach { resource ->
            when (resource) {
                is Resource.Loading -> _noteState.value = NoteState(true)
                is Resource.Success -> _noteState.value = NoteState(success = true)
                is Resource.Error -> _noteState.value = NoteState(error = "Error")
            }
        }.launchIn(GlobalScope)
    }
}