package com.pregnancy.edu.feature.authentication.register

import androidx.lifecycle.viewModelScope
import com.pregnancy.domain.model.User
import com.pregnancy.domain.usecase.RegisterUseCase
import com.pregnancy.edu.common.base.viewmodel.BaseViewModel
import com.pregnancy.edu.feature.authentication.register.event.RegisterEvent
import com.pregnancy.edu.feature.authentication.register.state.RegisterState
import com.pregnancy.edu.feature.authentication.register.state.RegisterViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<RegisterEvent, RegisterState, RegisterViewModelState>(
    initState = RegisterViewModelState()
) {
    override fun onTriggerEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.FullNameChanged -> updateFullName(event.fullName)
            is RegisterEvent.EmailChanged -> updateEmail(event.email)
            is RegisterEvent.PasswordChanged -> updatePassword(event.password)
            is RegisterEvent.ConfirmPasswordChanged -> updateConfirmPassword(event.confirmPassword)
            is RegisterEvent.AcceptedTermsChecked -> updateAcceptedTerms(event.checked)
            is RegisterEvent.Register -> register()
            is RegisterEvent.ErrorDismissed -> dismissError()
        }
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

    private fun register() {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            registerUseCase(
                fullName = viewModelState.value.fullName ?: "",
                email = viewModelState.value.email ?: "",
                password = viewModelState.value.password ?: "",
                confirmPassword = viewModelState.value.confirmPassword ?: ""
            ).reduce(RegisterEvent.Register)
        }
    }

    private fun dismissError() {
        viewModelState.update { it.copy(error = null) }
    }

    override fun <T> Result<T>.reduce(event: RegisterEvent) {
        onSuccess { result ->
            when (event) {
                is RegisterEvent.Register -> {
                    if (result is User) {
                        viewModelState.update {
                            it.copy(
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                }
                else -> Unit
            }
        }.onFailure { exception ->
            viewModelState.update {
                it.copy(
                    isLoading = false,
                    error = exception.message
                )
            }
        }
    }
}