package com.pregnancy.edu.feature.register.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.common.base.composable.Section
import com.pregnancy.edu.feature.register.viewmodel.RegisterEvent
import com.pregnancy.edu.feature.register.viewmodel.RegisterState

@Composable
fun RegisterContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    registerState: RegisterState,
    handleEvent: (event: RegisterEvent) -> Unit
) {
    Section(
        modifier = modifier
    ) {
        RegisterForm(
            fullName = registerState.fullName,
            onFullNameChange = { handleEvent(RegisterEvent.FullNameChanged(it)) },
            email = registerState.email,
            onEmailChange = { handleEvent(RegisterEvent.EmailChanged(it)) },
            password = registerState.password,
            onPasswordChange = { handleEvent(RegisterEvent.PasswordChanged(it)) },
            confirmPassword = registerState.confirmPassword,
            onConfirmPasswordChange = { handleEvent(RegisterEvent.ConfirmPasswordChanged(it)) },
            acceptedTerms = registerState.acceptedTerms,
            onAcceptedTermsChange = { handleEvent(RegisterEvent.AcceptedTermsChecked(it)) },
            enabledRegister = registerState.isFormValid,
            onRegisterClick = { handleEvent(RegisterEvent.Register) },
            onNavigateToLogin = {
                navController.popBackStack(
                    route = Destination.Login.route,
                    inclusive = false
                )
            }
        )
    }
}