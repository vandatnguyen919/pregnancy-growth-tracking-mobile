package com.pregnancy.edu.feature.login.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pregnancy.edu.R
import com.pregnancy.edu.common.base.composable.PasswordTextField
import com.pregnancy.edu.common.base.composable.PrimaryButton
import com.pregnancy.edu.common.base.composable.PrimaryTextField
import com.pregnancy.edu.common.base.composable.Section

@Preview(showBackground = true)
@Composable
fun LoginFormPreview() {
    LoginForm(
        email = "test@example.com",
        onEmailChange = {},
        password = "password",
        onPasswordChange = {},
        enabledLogin = true,
        onAuthenticate = {},
        onNavigateToRegister = {}
    )
}

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    email: String?,
    onEmailChange: (String) -> Unit,
    password: String?,
    onPasswordChange: (String) -> Unit,
    enabledLogin: Boolean,
    onAuthenticate: () -> Unit,
    onNavigateToRegister: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.title_sign_in),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 12.dp),
            color = Color.Black
        )
        // Email field
        PrimaryTextField(
            modifier = Modifier
                .padding(vertical = 8.dp),
            value = email,
            onValueChange = onEmailChange,
            label = stringResource(R.string.label_email)
        )
        // Password field
        PasswordTextField(
            modifier = Modifier
                .padding(vertical = 8.dp),
            value = password,
            onValueChange = onPasswordChange,
            label = stringResource(R.string.label_password),
        )
        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            text = stringResource(R.string.title_sign_in),
            enabled = enabledLogin,
            onClick = onAuthenticate,
            containerColor = Color(0xFFFAACAA)
        )
        CenterAlignedTextDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            text = stringResource(R.string.text_or)
        )
        GoogleSignInButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            onClick = { }
        )
        TextButton(
            modifier = Modifier.padding(top = 20.dp),
            onClick = onNavigateToRegister
        ) {
            Text(
                text = stringResource(R.string.text_create_account),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Blue
            )
        }
    }
}