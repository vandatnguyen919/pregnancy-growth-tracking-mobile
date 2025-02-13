package com.pregnancy.edu.feature.authentication.login.state

import com.pregnancy.edu.common.base.interfaces.ViewModelState
import com.pregnancy.edu.common.base.interfaces.ViewState

data class LoginViewModelState(
    val email: String? = null,
    val password: String? = null,
    val isAuthenticated: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
) : ViewModelState() {
    private val isFormValid: Boolean
        get() = email?.isNotEmpty() == true && password?.isNotEmpty() == true

    override fun toUiState(): ViewState {
        return LoginState(
            email = email,
            password = password,
            isLoginEnabled = isFormValid,
            isAuthenticated = isAuthenticated,
            isLoading = isLoading,
            error = error
        )
    }
}