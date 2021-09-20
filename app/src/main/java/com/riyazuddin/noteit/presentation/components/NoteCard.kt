package com.riyazuddin.noteit.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.riyazuddin.noteit.common.HexToJetpackColor.getColor
import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.presentation.ui.theme.Shapes

@Composable
fun NoteCard(
    note: Note,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClick()
            },
        elevation = 8.dp,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(
                modifier = Modifier
                    .width(30.dp)
                    .fillMaxHeight()
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(color = getColor(note.color))
            )

            Column(
                modifier = Modifier.padding(start = 3.dp)
            ) {
                Text(
                    text = note.title,
                    color = MaterialTheme.colors.onBackground,
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    maxLines = 1,
                    modifier = Modifier.padding(top = 5.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = note.content,
                    color = MaterialTheme.colors.onBackground,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                    maxLines = 2,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }
        }
    }
}