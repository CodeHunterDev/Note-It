package com.riyazuddin.noteit.presentation.signup

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
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
import androidx.navigation.NavOptions
import com.riyazuddin.noteit.R
import com.riyazuddin.noteit.common.Screen
import com.riyazuddin.noteit.presentation.components.StandardTextField

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(scrollState, true),
        contentAlignment = Alignment.Center
    ) {
        val signUpState = viewModel.authState.value

        Column(
            modifier = Modifier
                .matchParentSize(),
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
                val localFocusManager = LocalFocusManager.current

                Text(
                    text = stringResource(R.string.sign_up),
                    color = MaterialTheme.colors.onBackground,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                StandardTextField(
                    text = viewModel.email.value,
                    onValueChange = {
                        viewModel.setEmail(it)
                    },
                    hint = stringResource(R.string.email),
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
                    hint = stringResource(R.string.password),
                    keyboardType = KeyboardType.Password,
                    hidePasswordToggleAction = true,
                    imeAction = ImeAction.Next,
                    onImeAction = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                StandardTextField(
                    text = viewModel.repeatPassword.value,
                    onValueChange = {
                        viewModel.setRepeatPassword(it)
                    },
                    hint = stringResource(R.string.repeat_password),
                    keyboardType = KeyboardType.Password,
                    hidePasswordToggleAction = true,
                    imeAction = ImeAction.Done,
                    onImeAction = {
                        localFocusManager.clearFocus(true)
                    }
                )
                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    onClick = {
                        viewModel.signUp()
                    },
                    enabled = !(signUpState.isLoading),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_up),
                        fontSize = 20.sp
                    )
                }
            }
            Text(
                text = buildAnnotatedString {
                    append(stringResource(R.string.already_have_an_account))
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        )
                    ) {
                        append(stringResource(R.string.login))
                    }
                },
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                    }
            )
        }

        if (signUpState.isLoading) {
            CircularProgressIndicator()
        }
        if (signUpState.authSuccess) {
            if (signUpState.authSuccessMessage.isNotEmpty())
                Toast.makeText(
                    LocalContext.current,
                    signUpState.authSuccessMessage,
                    Toast.LENGTH_SHORT
                ).show()

            // At the top level of your kotlin file:

            val options = NavOptions.Builder()
                .setPopUpTo(Screen.LoginScreen.route, true).build()
            navController.navigate(Screen.NotesScreen.route, options)
        }
        if (signUpState.error.isNotEmpty()) {
            Toast.makeText(LocalContext.current, signUpState.error, Toast.LENGTH_SHORT).show()
            viewModel.reAssignSignUpState()
        }
    }
}