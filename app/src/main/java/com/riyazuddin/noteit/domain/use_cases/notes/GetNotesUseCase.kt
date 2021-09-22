package com.riyazuddin.noteit.domain.use_cases.notes

import com.riyazuddin.noteit.common.NoteOrder
import com.riyazuddin.noteit.common.OrderType
import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.domain.repository.INoteRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val noteRepository: INoteRepository
) {

    operator fun invoke(noteOrder: NoteOrder): Flow<List<Note>> {
        return noteRepository.getAllNotes().map { notes ->
            when(noteOrder.orderType){
                OrderType.Ascending -> {
                    when(noteOrder){
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                    }
                }
                OrderType.Descending -> {
                    when(noteOrder){
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                    }
                }
            }
        }
    }
}