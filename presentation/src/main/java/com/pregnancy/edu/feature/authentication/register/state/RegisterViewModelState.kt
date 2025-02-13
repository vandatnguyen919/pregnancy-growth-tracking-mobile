package com.pregnancy.edu.feature.authentication.register.state

import com.pregnancy.edu.common.base.interfaces.ViewModelState
import com.pregnancy.edu.common.base.interfaces.ViewState

data class RegisterViewModelState(
    val fullName: String? = null,
    val email: String? = null,
    val password: String? = null,
    val confirmPassword: String? = null,
    val acceptedTerms: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
) : ViewModelState() {

    override fun toUiState(): ViewState {
        return RegisterState(
            fullName = fullName,
            email = email,
            password = password,
            confirmPassword = confirmPassword,
            acceptedTerms = acceptedTerms,
            isLoading = isLoading,
            error = error
        )
    }
}