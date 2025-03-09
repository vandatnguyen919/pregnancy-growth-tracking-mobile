package com.pregnancy.edu.feature.authentication.otp.state

import com.pregnancy.edu.common.base.interfaces.ViewModelState
import com.pregnancy.edu.common.base.interfaces.ViewState

data class OtpViewModelState(
    val otpVerificationSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
) : ViewModelState(){

    override fun toUiState(): ViewState {
        return OtpState(
            otpVerificationSuccess = otpVerificationSuccess,
            isLoading = isLoading,
            error = error
        )
    }
}