package com.pregnancy.edu.feature.login.viewmodel

sealed class LoginEvent {

    class EmailChanged(val email: String) : LoginEvent()

    class PasswordChanged(val password: String) : LoginEvent()

    data object Authenticate : LoginEvent()
}