package com.riyazuddin.noteit.presentation.note

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.riyazuddin.noteit.R
import com.riyazuddin.noteit.data.model.Note

@Composable
fun CreateNoteScreen(
    navController: NavController,
    note: Note,
    viewModel: CreateOrUpdateNoteViewModel = hiltViewModel()
) {

    val noteState = viewModel.noteState.value

    val isFirstTime = remember {
        mutableStateOf(true)
    }

    if (isFirstTime.value) {
        viewModel.setNote(note)
        isFirstTime.value = false
    }

    val localFocusManager = LocalFocusManager.current
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.matchParentSize()
        ) {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { TODO() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIos,
                            contentDescription = stringResource(R.string.back),
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.saveNote()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.apply),
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = viewModel.title.value,
                    onValueChange = {
                        viewModel.setTitle(it)
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            localFocusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    placeholder = {
                        Text(
                            text = "Title",
                            color = MaterialTheme.colors.onBackground,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colors.onBackground,
                        backgroundColor = MaterialTheme.colors.background,
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    singleLine = false,
                    maxLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Date", modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                TextField(
                    value = viewModel.content.value,
                    onValueChange = {
                        viewModel.setContent(it)
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            localFocusManager.clearFocus(true)
                        }
                    ),
                    placeholder = {
                        Text(
                            text = "Type Something here...",
                            color = MaterialTheme.colors.onBackground,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal,
                            )
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colors.onBackground,
                        backgroundColor = MaterialTheme.colors.background,
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                    singleLine = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }
        }

        if (noteState.isLoading)
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        if (noteState.success)
            Text(
                text = "Saved",
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .align(Alignment.BottomCenter)
            )
        if (noteState.error.isNotEmpty())
            Text(text = noteState.error)
    }

}