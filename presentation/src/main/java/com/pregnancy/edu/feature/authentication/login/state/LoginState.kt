package com.pregnancy.edu.feature.authentication.login.state

import com.pregnancy.edu.common.base.interfaces.ViewState

data class LoginState(
    val email: String? = null,
    val password: String? = null,
    val isLoginEnabled: Boolean = false,
    val isAuthenticated: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
) : ViewState()