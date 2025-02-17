package com.pregnancy.edu.feature.authentication.register.event

import com.pregnancy.edu.common.base.interfaces.ViewEvent

sealed class RegisterEvent : ViewEvent {

    class FullNameChanged(val fullName: String) : RegisterEvent()

    class EmailChanged(val email: String) : RegisterEvent()

    class PasswordChanged(val password: String) : RegisterEvent()

    class ConfirmPasswordChanged(val confirmPassword: String) : RegisterEvent()

    class AcceptedTermsChecked(val checked: Boolean) : RegisterEvent()

    data object Register : RegisterEvent()

    data object ErrorDismissed : RegisterEvent()
}