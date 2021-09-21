package com.riyazuddin.noteit.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riyazuddin.noteit.common.Resource
import com.riyazuddin.noteit.domain.use_cases.get_notes.GetNotesUseCase
import com.riyazuddin.noteit.presentation.states.GetNotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetNotesViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase
) : ViewModel() {

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing
    fun setRefreshing(isRefreshing: Boolean){
        _isRefreshing.value = isRefreshing
        if (isRefreshing)
            getAllNotes()
    }

    private val _getNoteState = mutableStateOf(GetNotesState())
    val getNoteState: State<GetNotesState> = _getNoteState

    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        getNotesUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> _getNoteState.value = GetNotesState(true)
                is Resource.Success -> _getNoteState.value =
                    GetNotesState(success = true, notes = resource.data ?: emptyList())
                is Resource.Error -> _getNoteState.value = GetNotesState(error = "error")
            }
        }.launchIn(viewModelScope)
    }
}