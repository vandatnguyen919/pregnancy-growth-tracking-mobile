package com.pregnancy.edu.feature.register.viewmodel

import androidx.lifecycle.ViewModel
import com.pregnancy.edu.feature.login.viewmodel.LoginEvent
import kotlinx.coroutines.flow.MutableStateFlow

class RegisterViewModel : ViewModel() {

    val uiState = MutableStateFlow(RegisterState())

    private fun updateFullName(fullName: String) {
        uiState.value = uiState.value.copy(
            fullName = fullName
        )
    }

    private fun updateEmail(email: String) {
        uiState.value = uiState.value.copy(
            email = email
        )
    }

    private fun updatePassword(password: String) {
        uiState.value = uiState.value.copy(
            password = password
        )
    }

    private fun updateConfirmPassword(confirmPassword: String) {
        uiState.value = uiState.value.copy(
            confirmPassword = confirmPassword
        )
    }

    private fun updateAcceptedTerms(checked: Boolean) {
        uiState.value = uiState.value.copy(
            acceptedTerms = checked
        )
    }

    fun handleEvent(registerEvent: RegisterEvent) {
        when (registerEvent) {
            is RegisterEvent.FullNameChanged -> updateFullName(registerEvent.fullName)
            is RegisterEvent.EmailChanged -> updateEmail(registerEvent.email)
            is RegisterEvent.PasswordChanged -> updatePassword(registerEvent.password)
            is RegisterEvent.ConfirmPasswordChanged -> updateConfirmPassword(registerEvent.confirmPassword)
            is RegisterEvent.AcceptedTermsChecked -> updateAcceptedTerms(registerEvent.checked)
            is RegisterEvent.Register -> TODO()
        }
    }
}