package com.pregnancy.edu.feature.authentication.login

import androidx.lifecycle.viewModelScope
import com.pregnancy.domain.model.authentication.User
import com.pregnancy.domain.usecase.LoginUseCase
import com.pregnancy.edu.common.base.viewmodel.BaseViewModel
import com.pregnancy.edu.feature.authentication.login.event.LoginEvent
import com.pregnancy.edu.feature.authentication.login.state.LoginState
import com.pregnancy.edu.feature.authentication.login.state.LoginViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginEvent, LoginState, LoginViewModelState>(
    initState = LoginViewModelState()
) {
    override fun onTriggerEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> updateEmail(event.email)
            is LoginEvent.PasswordChanged -> updatePassword(event.password)
            is LoginEvent.Authenticate -> authenticate()
            is LoginEvent.ErrorDismissed -> dismissError()
        }
    }

    private fun updateEmail(email: String) {
        viewModelState.update { it.copy(email = email) }
    }

    private fun updatePassword(password: String) {
        viewModelState.update { it.copy(password = password) }
    }

    private fun authenticate() {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase(
                email = viewModelState.value.email ?: "",
                password = viewModelState.value.password ?: ""
            ).reduce(LoginEvent.Authenticate)
        }
    }

    private fun dismissError() {
        viewModelState.update { it.copy(error = null) }
    }

    override fun <T> Result<T>.reduce(event: LoginEvent) {
        onSuccess { result ->
            when (event) {
                is LoginEvent.Authenticate -> {
                    if (result is User) {
                        viewModelState.update {
                            it.copy(
                                isLoading = false,
                                isAuthenticated = true,
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
//                    isAuthenticated = false,
                    isAuthenticated = true,
                    error = exception.message
                )
            }
        }
    }
}