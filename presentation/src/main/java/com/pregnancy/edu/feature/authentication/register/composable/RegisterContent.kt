package com.pregnancy.edu.feature.authentication.register.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.common.base.composable.Section
import com.pregnancy.edu.feature.authentication.register.event.RegisterEvent
import com.pregnancy.edu.feature.authentication.register.state.RegisterState

@Composable
fun RegisterContent(
    modifier: Modifier = Modifier,
    registerState: RegisterState,
    onTriggerEvent: (event: RegisterEvent) -> Unit,
    popUpToLogin: () -> Unit
) {
    Section(
        modifier = modifier
    ) {
        RegisterForm(
            fullName = registerState.fullName,
            onFullNameChange = { onTriggerEvent(RegisterEvent.FullNameChanged(it)) },
            email = registerState.email,
            onEmailChange = { onTriggerEvent(RegisterEvent.EmailChanged(it)) },
            password = registerState.password,
            onPasswordChange = { onTriggerEvent(RegisterEvent.PasswordChanged(it)) },
            confirmPassword = registerState.confirmPassword,
            onConfirmPasswordChange = { onTriggerEvent(RegisterEvent.ConfirmPasswordChanged(it)) },
            acceptedTerms = registerState.acceptedTerms,
            onAcceptedTermsChange = { onTriggerEvent(RegisterEvent.AcceptedTermsChecked(it)) },
            enabledRegister = true,
            onRegisterClick = { onTriggerEvent(RegisterEvent.Register) },
            popUpToLogin = popUpToLogin
        )
    }
}