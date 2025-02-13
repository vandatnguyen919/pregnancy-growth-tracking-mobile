package com.pregnancy.edu.feature.authentication.login.event

import com.pregnancy.edu.common.base.interfaces.ViewEvent

sealed class LoginEvent : ViewEvent {

    class EmailChanged(val email: String) : LoginEvent()

    class PasswordChanged(val password: String) : LoginEvent()

    data object Authenticate : LoginEvent()
}