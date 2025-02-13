package com.pregnancy.edu.feature.authentication.login.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pregnancy.edu.common.base.Destination
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
        navController = navController,
        loginState = loginState,
        onTriggerEvent = {}
    )
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    loginState: LoginState,
    onTriggerEvent: (event: LoginEvent) -> Unit
) {
    Section(
        modifier = modifier
    ) {
        LoginForm(
            onNavigateToRegister = {
                navController.navigate(Destination.Register.route) {
                    popUpTo(Destination.Login.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            email = loginState.email,
            onEmailChange = { onTriggerEvent(LoginEvent.EmailChanged(it)) },
            password = loginState.password,
            onPasswordChange = { onTriggerEvent(LoginEvent.PasswordChanged(it)) },
            enabledLogin = loginState.isLoginEnabled,
            onAuthenticate = { onTriggerEvent(LoginEvent.Authenticate) }
        )
    }
}