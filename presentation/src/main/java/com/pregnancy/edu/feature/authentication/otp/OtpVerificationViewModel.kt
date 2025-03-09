package com.pregnancy.edu.feature.authentication.otp

import androidx.lifecycle.viewModelScope
import com.pregnancy.domain.usecase.SendOtpUseCase
import com.pregnancy.domain.usecase.ValidateEmailUseCase
import com.pregnancy.edu.common.base.viewmodel.BaseViewModel
import com.pregnancy.edu.feature.authentication.otp.event.OtpEvent
import com.pregnancy.edu.feature.authentication.otp.state.OtpState
import com.pregnancy.edu.feature.authentication.otp.state.OtpViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpVerificationViewModel @Inject constructor(
    private val sendOtpUseCase: SendOtpUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase
) : BaseViewModel<OtpEvent, OtpState, OtpViewModelState>(
    initState = OtpViewModelState()
) {

    override fun onTriggerEvent(event: OtpEvent) {
        when (event) {
            is OtpEvent.SendOtp -> sendOtp(event.email)
            is OtpEvent.ValidateEmail -> validateEmail(event.email, event.otp)
            OtpEvent.ErrorDismissed -> dismissError()
        }
    }

    private fun validateEmail(email: String, otp: String) {
        viewModelState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            validateEmailUseCase(email, otp).onSuccess {
                viewModelState.update { it.copy(isLoading = false, otpVerificationSuccess = true) }
            }.onFailure { exception ->
                viewModelState.update { it.copy(isLoading = false, error = exception.message) }
            }
        }
    }

    private fun sendOtp(email: String) {
        viewModelScope.launch {
            sendOtpUseCase(email).onSuccess {
                viewModelState.update { it.copy(isLoading = false) }
            }.onFailure { exception ->
                viewModelState.update { it.copy(isLoading = false, error = exception.message) }
            }
        }
    }

    private fun dismissError() {
        viewModelState.update { it.copy(error = null) }
    }

    override fun <T> Result<T>.reduce(event: OtpEvent) {
        TODO()
    }
}