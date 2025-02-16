package com.pregnancy.edu.common.base.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pregnancy.edu.R
import com.pregnancy.edu.common.base.interfaces.KeyboardAction

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String? = null,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardAction: KeyboardAction = KeyboardAction.None
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        ),
        value = value ?: "",
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        enabled = enabled,
        readOnly = readOnly,
        isError = isError,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = if (passwordVisible) R.drawable.ic_eye else R.drawable.ic_eye_slash_fill),
                    contentDescription = stringResource(if (passwordVisible) R.string.cd_hide_password else R.string.cd_show_password)
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color(0xFFFAACAA),
            unfocusedIndicatorColor = Color.Gray,
            focusedLabelColor = Color(0xFFFAACAA),
            unfocusedLabelColor = Color.Gray,
            errorContainerColor = Color.White,
            errorIndicatorColor = MaterialTheme.colorScheme.error,
            errorLabelColor = MaterialTheme.colorScheme.error,
            cursorColor = Color(0xFFFAACAA)
        ),
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = when (keyboardAction) {
                is KeyboardAction.Next -> ImeAction.Next
                is KeyboardAction.Done -> ImeAction.Done
                KeyboardAction.None -> ImeAction.Done
            }
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                if (keyboardAction is KeyboardAction.Next) {
                    keyboardAction.onClick()
                }
            },
            onDone = {
                if (keyboardAction is KeyboardAction.Done) {
                    keyboardAction.onClick()
                }
            }
        )
    )
}