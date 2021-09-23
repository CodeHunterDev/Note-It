package com.riyazuddin.noteit.presentation.note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riyazuddin.noteit.data.model.InvalidNoteException
import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.domain.use_cases.notes.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddOrEditNoteViewModel @Inject constructor(
    private val noteUseCase: NotesUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var currentNoteId: String = UUID.randomUUID().toString()
    private var noteTimestamp: Long = System.currentTimeMillis()

    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(
            hint = "Title Here..."
        )
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(
        NoteTextFieldState(
            hint = "Content Here..."
        )
    )
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("noteId")?.let { noteId ->
            if (noteId != "EMPTY") {
                viewModelScope.launch {
                    noteUseCase.getNote(noteId)?.also {
                        currentNoteId = it.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = it.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = it.content,
                            isHintVisible = false
                        )
                        _noteColor.value = it.color
                        noteTimestamp = it.timestamp
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.title
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.content
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCase.insertNoteUseCase(
                            Note(
                                id = currentNoteId,
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = noteTimestamp,
                                color = noteColor.value
                            )
                        )
                        _eventFlow.emit(UIEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(e.message ?: "Couldn't save the note.")
                        )
                    }
                }
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
        object SaveNote : UIEvent()
    }
}

//    fun setNote(note: Note) {
//        setId(note.id)
//        setTitle(note.title)
//        setContent(note.content)
//        setDate(note.timestamp)
//        setOwner(note.owner)
//        setColor(note.color)
//    }
//
//    private val _id = mutableStateOf("")
//    val id: State<String> = _id
//    private fun setId(id: String) {
//        _id.value = id
//    }
//
//    private val _title = mutableStateOf("")
//    val title: State<String> = _title
//    fun setTitle(title: String) {
//        _title.value = title
//    }
//
//    private val _content = mutableStateOf("")
//    val content: State<String> = _content
//    fun setContent(content: String) {
//        _content.value = content
//    }
//
//    private val _date = mutableStateOf(0L)
//    val date: State<Long> = _date
//    private fun setDate(date: Long) {
//        _date.value = date
//    }
//
//    private val _owner = mutableStateOf("")
//    private val owner: State<String> = _owner
//    private fun setOwner(owner: String) {
//        _owner.value = owner
//    }
//
//    private val _color = mutableStateOf(0)
//    val color: State<Int> = _color
//    fun setColor(color: Int) {
//        _color.value = color
//    }
//
//    private val _noteState = mutableStateOf(NoteState())
//    val noteState: State<NoteState> = _noteState
//    @DelicateCoroutinesApi
//    fun saveNote() {
////        createOrUpdateNote(
////            id.value,
////            title.value,
////            content.value,
////            date.value,
////            owner.value,
////            color.value
////        ).onEach { resource ->
////            when (resource) {
////                is Resource.Loading -> _noteState.value = NoteState(true)
////                is Resource.Success -> _noteState.value = NoteState(success = true)
////                is Resource.Error -> _noteState.value = NoteState(error = "Error")
////            }
////        }.launchIn(GlobalScope)
//    }
//}