package com.pregnancy.edu.feature.register.viewmodel

data class RegisterState(
    val fullName: String? = null,
    val email: String? = null,
    val password: String? = null,
    val confirmPassword: String? = null,
    val acceptedTerms: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val isFormValid: Boolean
        get() = !fullName.isNullOrBlank() &&
                !email.isNullOrBlank() &&
                email.contains("@") &&
                !password.isNullOrBlank() &&
//                password?.length ?: 0 >= 6 &&
                !confirmPassword.isNullOrBlank() &&
                password == confirmPassword &&
                acceptedTerms
}