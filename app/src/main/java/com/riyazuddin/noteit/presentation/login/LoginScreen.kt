package com.riyazuddin.noteit.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.riyazuddin.noteit.R
import com.riyazuddin.noteit.presentation.components.StandardTextField
import com.riyazuddin.noteit.presentation.utill.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(state = scrollState, enabled = true),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = stringResource(R.string.app_name),
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 70.sp,
            fontStyle = FontStyle.Italic
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.login),
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            val localFocusManager = LocalFocusManager.current
            StandardTextField(
                text = viewModel.email.value,
                onValueChange = {
                    viewModel.setEmail(it)
                },
                hint = stringResource(id = R.string.email),
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                onImeAction = {
                    localFocusManager.moveFocus(FocusDirection.Down)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            StandardTextField(
                text = viewModel.password.value,
                onValueChange = {
                    viewModel.setPassword(it)
                },
                hint = stringResource(id = R.string.password),
                keyboardType = KeyboardType.Password,
//                error = viewModel.passwordError.value,
                passwordHidden = viewModel.passwordToggleState.value,
                passwordToggle = {
                    viewModel.setPasswordToggleState(it)
                },
                imeAction = ImeAction.Done,
                onImeAction = {
                    localFocusManager.clearFocus(true)
                }
            )
            Spacer(modifier = Modifier.height(18.dp))
            Button(
                onClick = {
                    navController.navigate(Screen.NotesScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    fontSize = 20.sp
                )
            }
        }
        Text(
            text = buildAnnotatedString {
                append(stringResource(R.string.dont_have_an_account))
                append(" ")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                ) {
                    append(stringResource(R.string.sign_up))
                }
            },
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .clickable {
                    navController.navigate(Screen.SignUpScreen.route)
                }
        )
    }
}