package com.pregnancy.edu.feature.login.viewmodel

data class LoginState(
    val email: String? = null,
    val password: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val isFormValid: Boolean
        get() = email?.isNotEmpty() == true && password?.isNotEmpty() == true
}