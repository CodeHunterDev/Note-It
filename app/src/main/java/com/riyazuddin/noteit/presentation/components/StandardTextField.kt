package com.riyazuddin.noteit.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.riyazuddin.noteit.R

@Composable
fun StandardTextField(
    text: String,
    onValueChange: (String) -> Unit,
    hint: String,
    error: String = "",
    keyboardType: KeyboardType,
    passwordToggle: (Boolean) -> Unit = {},
    passwordHidden: Boolean = true,
    hidePasswordToggleAction: Boolean = false,
    imeAction: ImeAction,
    onImeAction: () -> Unit
) {

    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(text = hint)
        },
        singleLine = true,
        isError = error != "",
        visualTransformation = if (passwordHidden && keyboardType == KeyboardType.Password)
            PasswordVisualTransformation()
        else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        trailingIcon = {
            if (keyboardType == KeyboardType.Password && !hidePasswordToggleAction) {
                IconButton(onClick = {
                    passwordToggle(!passwordHidden)
                }) {
                    val visibilityIcon =
                        if (passwordHidden) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    val tintColor =
                        if (passwordHidden) MaterialTheme.colors.onBackground else MaterialTheme.colors.primary
                    val description =
                        if (passwordHidden) stringResource(R.string.show_password) else stringResource(
                            R.string.hide_password
                        )
                    Icon(
                        imageVector = visibilityIcon,
                        contentDescription = description,
                        tint = tintColor
                    )
                }
            }
        },
        keyboardActions = KeyboardActions{
            onImeAction()
        }
    )
    if (error.isNotEmpty())
        Text(text = error, color = Color.Red, fontSize = 12.sp)
}