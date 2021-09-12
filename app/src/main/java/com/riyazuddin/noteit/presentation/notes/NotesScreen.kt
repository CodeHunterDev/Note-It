package com.riyazuddin.noteit.presentation.notes

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.presentation.components.NoteCard
import com.riyazuddin.noteit.presentation.utill.Screen


@Composable
fun NotesScreen(
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            TopAppBar(
                title = { Text("Notes") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.SettingsScreen.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            )

            val context = LocalContext.current
            LazyColumn {
                for (i in 1..10) {
                    item {
                        NoteCard(
                            Note(
                                "1",
                                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                                123L,
                                "",
                                ""
                            )
                        ) {
                            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        }
        FloatingActionButton(
            onClick = {
                navController.navigate(Screen.CreateNoteScreen.route)
            },
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Create new Note",
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}