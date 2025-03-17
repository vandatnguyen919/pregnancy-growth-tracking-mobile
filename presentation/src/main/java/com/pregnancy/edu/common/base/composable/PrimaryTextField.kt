package com.pregnancy.edu.common.base.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pregnancy.edu.common.base.interfaces.KeyboardAction

@Preview
@Composable
fun PrimaryTextFieldPreview() {
    PrimaryTextField(
        value = "",
        onValueChange = {},
        label = "Email"
    )
}

@Composable
fun PrimaryTextField(
    modifier: Modifier = Modifier,
    value: String? = null,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    keyboardAction: KeyboardAction = KeyboardAction.None
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value ?: "",
        onValueChange = onValueChange,
        label = { Text(label) },
        textStyle = textStyle,
        singleLine = singleLine,
        maxLines = maxLines,
        enabled = enabled,
        readOnly = readOnly,
        isError = isError,
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
