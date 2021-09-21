package com.riyazuddin.noteit.presentation.notes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.riyazuddin.noteit.R
import com.riyazuddin.noteit.common.Screen
import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.presentation.components.NoteCard


@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: GetNotesViewModel = hiltViewModel()
) {

    val getNotesState = viewModel.getNoteState.value

    fun navigationToNote(note: Note) {
        val user = Gson().toJson(note)
        navController.navigate("${Screen.CreateNoteScreen.route}/$user")
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.matchParentSize()) {
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
            LazyColumn {
                items(getNotesState.notes) { note ->
                    NoteCard(note) {
                        navigationToNote(note)
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = {
                navigationToNote(
                    Note(
                        title = "",
                        content = "",
                        date = System.currentTimeMillis(),
                        color = "FF0036"
                    )
                )
            },
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = 16.dp, vertical = 16.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(R.string.create_new_note),
                tint = MaterialTheme.colors.onPrimary
            )
        }

        if (getNotesState.isLoading)
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        if (getNotesState.success)
            Text(
                text = "Got",
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .align(Alignment.BottomCenter)
            )
        if (getNotesState.error.isNotEmpty())
            Text(text = getNotesState.error)
    }
}