package com.pregnancy.edu.feature.authentication.login

import androidx.lifecycle.viewModelScope
import com.pregnancy.edu.common.base.viewmodel.BaseViewModel
import com.pregnancy.edu.feature.authentication.login.state.LoginState
import com.pregnancy.edu.feature.authentication.login.event.LoginEvent
import com.pregnancy.edu.feature.authentication.login.state.LoginViewModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : BaseViewModel<LoginEvent, LoginState, LoginViewModelState>(
    initState = LoginViewModelState()
) {
    override fun onTriggerEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> updateEmail(event.email)
            is LoginEvent.PasswordChanged -> updatePassword(event.password)
            is LoginEvent.Authenticate -> authenticate()
        }
    }

    override fun <T> Result<T>.reduce(event: LoginEvent) {
        TODO("Not yet implemented")
    }

    private fun updateEmail(email: String) {
        viewModelState.update { it.copy(email = email) }
    }

    private fun updatePassword(password: String) {
        viewModelState.update { it.copy(password = password) }
    }

    private fun authenticate() {
        viewModelState.update { it.copy(isLoading = true) }

        // Simulate network request
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000L)
            withContext(Dispatchers.Main) {
                viewModelState.update {
                    if (it.email.equals("admin@example.com") && it.password.equals("admin")) {
                        it.copy(isLoading = false, isAuthenticated = true, error = null)
                    } else {
                        it.copy(isLoading = false, isAuthenticated = false, error = "Invalid email or password")
                    }
                }
            }
        }
    }
}