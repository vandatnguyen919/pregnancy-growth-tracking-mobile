package com.pregnancy.edu.feature.authentication.login.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.pregnancy.edu.R

@Composable
fun AuthenticationErrorDialog(
    modifier: Modifier = Modifier,
    error: String,
    dismissError: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = dismissError,
        confirmButton = {
            TextButton(
                onClick = {
                    dismissError()
                }
            ) {
                Text(text = stringResource(
                    id = R.string.error_action))
            }
        },
        title = {
            Text(
                text = stringResource(
                    id = R.string.error_title),
                fontSize = 18.sp
            )
        },
        text = {
            Text(
                text = error
            )
        }
    )
}