package com.pregnancy.edu.feature.authentication.otp.event

import com.pregnancy.edu.common.base.interfaces.ViewEvent

sealed class OtpEvent : ViewEvent {

    data class SendOtp(val email: String) : OtpEvent()

    data class ValidateEmail(val email: String, val otp: String) : OtpEvent()

    data object ErrorDismissed : OtpEvent()
}