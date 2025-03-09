package com.pregnancy.edu.feature.authentication.otp.state

import com.pregnancy.edu.common.base.interfaces.ViewState

data class OtpState(
    val otpVerificationSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    ) : ViewState() {
}