package com.riyazuddin.noteit.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 70.sp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Text(
                text = stringResource(R.string.login),
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
                hint = stringResource(id = R.string.email),
                keyboardType = KeyboardType.Email
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
                }
            )
            Spacer(modifier = Modifier.height(18.dp))
            Button(
                onClick = { /*TODO*/ },
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
                .align(Alignment.BottomCenter)
                .clickable {
                    navController.navigate(Screen.SignUpScreen.route)
                }
        )
    }
}