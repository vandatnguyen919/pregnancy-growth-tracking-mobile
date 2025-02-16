package com.pregnancy.edu.feature.authentication.login.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.pregnancy.edu.common.base.composable.Section
import com.pregnancy.edu.feature.authentication.login.event.LoginEvent
import com.pregnancy.edu.feature.authentication.login.state.LoginState

@Preview
@Composable
fun LoginContentPreview() {
    val navController = rememberNavController()
    val loginState = LoginState(
        email = "test@example.com",
        password = "password",
    )
    LoginContent(
        onNavigateToRegister = { },
        loginState = loginState,
        onTriggerEvent = {}
    )
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    loginState: LoginState,
    onTriggerEvent: (event: LoginEvent) -> Unit,
    onNavigateToRegister: () -> Unit,
) {
    Section(
        modifier = modifier
    ) {
        LoginForm(
            onNavigateToRegister = onNavigateToRegister,
            email = loginState.email,
            onEmailChange = { onTriggerEvent(LoginEvent.EmailChanged(it)) },
            password = loginState.password,
            onPasswordChange = { onTriggerEvent(LoginEvent.PasswordChanged(it)) },
            enabledLogin = loginState.isLoginEnabled,
            onAuthenticate = { onTriggerEvent(LoginEvent.Authenticate) }
        )
    }
}