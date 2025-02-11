package com.pregnancy.edu.feature.register.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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

@Composable
fun RegisterForm(
    modifier: Modifier = Modifier,
    fullName: String?,
    onFullNameChange: (String) -> Unit,
    email: String?,
    onEmailChange: (String) -> Unit,
    password: String?,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String?,
    onConfirmPasswordChange: (String) -> Unit,
    acceptedTerms: Boolean = false,
    onAcceptedTermsChange: (Boolean) -> Unit,
    enabledRegister: Boolean,
    onRegisterClick: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.title_sign_up),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 12.dp),
            color = Color.Black
        )
        // Full name field
        PrimaryTextField(
            modifier = Modifier
                .padding(vertical = 8.dp),
            value = fullName,
            onValueChange = onFullNameChange,
            label = stringResource(R.string.label_full_name)
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
        // Confirm Password field
        PasswordTextField(
            modifier = Modifier
                .padding(vertical = 8.dp),
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = stringResource(R.string.label_confirm_password),
        )
        TermsCheckbox(
            modifier = Modifier
                .padding(top = 8.dp),
            checked = acceptedTerms,
            onCheckedChange = onAcceptedTermsChange,
            onTermsClicked = { /* Navigate to Terms of Use */ },
            onPrivacyClicked = { /* Navigate to Privacy Policy */ }
        )
        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            text = stringResource(R.string.text_create_account),
            enabled = enabledRegister,
            onClick = onRegisterClick,
            containerColor = Color(0xFFFAACAA)
        )
        TextButton(
            modifier = Modifier.padding(top = 20.dp),
            onClick = onNavigateToLogin
        ) {
            Text(
                text = stringResource(R.string.text_back_to_login),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Blue
            )
        }
    }
}