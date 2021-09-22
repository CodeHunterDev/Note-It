package com.riyazuddin.noteit.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riyazuddin.noteit.common.NoteOrder
import com.riyazuddin.noteit.common.OrderType
import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.domain.use_cases.notes.NotesEvents
import com.riyazuddin.noteit.domain.use_cases.notes.GetNotesUseCase
import com.riyazuddin.noteit.domain.use_cases.notes.NotesUseCases
import com.riyazuddin.noteit.presentation.states.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {

    private var getNotesJob: Job? = null

    private val _notesState = mutableStateOf(NotesState())
    val notesState: State<NotesState> = _notesState

    private var recentlyDeletedNote: Note? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(onEvent: NotesEvents){
        when (onEvent) {
            is NotesEvents.DeleteNote -> {
                viewModelScope.launch {
                    notesUseCases.deleteNoteUseCase(onEvent.note)
                    recentlyDeletedNote = onEvent.note
                }
            }
            is NotesEvents.Order -> {
                if (notesState.value.noteOrder == onEvent.noteOrder &&
                    notesState.value.noteOrder.orderType == onEvent.noteOrder.orderType
                ) {
                    return
                }
                getNotes(onEvent.noteOrder)
            }
            NotesEvents.RestoreRecentlyDeletedNote -> {
                viewModelScope.launch {
                    notesUseCases.insertNoteUseCase(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            NotesEvents.ToggleOrderSection -> {
                _notesState.value = notesState.value.copy(
                    isOrderSelectionVisible = !notesState.value.isOrderSelectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder){
        getNotesJob?.cancel()
        getNotesJob = notesUseCases.getNotes(noteOrder).onEach {
            _notesState.value = _notesState.value.copy(
                notes = it,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }

//    private fun getAllNotes(noteOrder: NoteOrder) {
//        val a = getNotesUseCase(noteOrder)
//        getNotesUseCase(noteOrder).onEach { resource ->
//            when (resource) {
//                is Resource.Loading -> _getNoteState.value = NotesState(true)
//                is Resource.Success -> _getNoteState.value =
//                    NotesState(success = true, notes = resource.data ?: emptyList())
//                is Resource.Error -> _getNoteState.value =
//                    NotesState(
//                        notes = resource.data ?: emptyList(),
//                        error = resource.message ?: AN_UNKNOWN_ERROR_OCCURRED
//                    )
//            }
//        }.launchIn(viewModelScope)
//    }
}