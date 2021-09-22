package com.riyazuddin.noteit.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.riyazuddin.noteit.common.NoteOrder
import com.riyazuddin.noteit.common.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onNoteOrderChange: (NoteOrder) -> Unit
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            CustomRadioButton(
                text = "Title",
                selected = noteOrder is NoteOrder.Title,
                onClick = {
                    onNoteOrderChange(NoteOrder.Title(noteOrder.orderType))
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomRadioButton(
                text = "Date",
                selected = noteOrder is NoteOrder.Date,
                onClick = {
                    onNoteOrderChange(NoteOrder.Date(noteOrder.orderType))
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomRadioButton(
                text = "Color",
                selected = noteOrder is NoteOrder.Color,
                onClick = {
                    onNoteOrderChange(NoteOrder.Color(noteOrder.orderType))
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            CustomRadioButton(
                text = "Ascending",
                selected = noteOrder.orderType is OrderType.Ascending,
                onClick = {
                    onNoteOrderChange(noteOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomRadioButton(
                text = "Descending",
                selected = noteOrder.orderType is OrderType.Descending,
                onClick = {
                    onNoteOrderChange(noteOrder.copy(OrderType.Descending))
                }
            )
        }

    }
}