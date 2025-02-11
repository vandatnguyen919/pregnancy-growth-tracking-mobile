package com.pregnancy.edu.feature.register.viewmodel

import com.pregnancy.edu.feature.login.viewmodel.LoginEvent

sealed class RegisterEvent {

    class FullNameChanged(val fullName: String) : RegisterEvent()

    class EmailChanged(val email: String) : RegisterEvent()

    class PasswordChanged(val password: String) : RegisterEvent()

    class ConfirmPasswordChanged(val confirmPassword: String) : RegisterEvent()

    class AcceptedTermsChecked(val checked: Boolean) : RegisterEvent()

    data object Register : RegisterEvent()
}