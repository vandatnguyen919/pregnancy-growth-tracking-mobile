package com.pregnancy.edu.feature.authentication.register.state

import com.pregnancy.edu.common.base.interfaces.ViewState

data class RegisterState(
    val fullName: String? = null,
    val fullNameError: Boolean = true,
    val email: String? = null,
    val emailError: Boolean = true,
    val password: String? = null,
    val passwordError: Boolean = true,
    val confirmPassword: String? = null,
    val confirmPasswordError: Boolean = true,
    val acceptedTerms: Boolean = false,
    val isLoading: Boolean = false,
    val isRegisterSuccess: Boolean = false,
    val error: String? = null
) : ViewState()