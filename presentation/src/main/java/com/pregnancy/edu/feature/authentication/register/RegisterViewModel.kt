package com.pregnancy.edu.feature.authentication.register

import com.pregnancy.edu.common.base.viewmodel.BaseViewModel
import com.pregnancy.edu.feature.authentication.register.event.RegisterEvent
import com.pregnancy.edu.feature.authentication.register.state.RegisterState
import com.pregnancy.edu.feature.authentication.register.state.RegisterViewModelState
import kotlinx.coroutines.flow.update

class RegisterViewModel : BaseViewModel<RegisterEvent, RegisterState, RegisterViewModelState>(
    initState = RegisterViewModelState()
) {
    override fun onTriggerEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.FullNameChanged -> updateFullName(event.fullName)
            is RegisterEvent.EmailChanged -> updateEmail(event.email)
            is RegisterEvent.PasswordChanged -> updatePassword(event.password)
            is RegisterEvent.ConfirmPasswordChanged -> updateConfirmPassword(event.confirmPassword)
            is RegisterEvent.AcceptedTermsChecked -> updateAcceptedTerms(event.checked)
            is RegisterEvent.Register -> TODO()
        }
    }

    override fun <T> Result<T>.reduce(event: RegisterEvent) {
        TODO("Not yet implemented")
    }

    private fun updateFullName(fullName: String) {
        viewModelState.update { it.copy(fullName = fullName) }
    }

    private fun updateEmail(email: String) {
        viewModelState.update { it.copy(email = email) }
    }

    private fun updatePassword(password: String) {
        viewModelState.update { it.copy(password = password) }
    }

    private fun updateConfirmPassword(confirmPassword: String) {
        viewModelState.update { it.copy(confirmPassword = confirmPassword) }
    }

    private fun updateAcceptedTerms(checked: Boolean) {
        viewModelState.update { it.copy(acceptedTerms = checked) }
    }
}