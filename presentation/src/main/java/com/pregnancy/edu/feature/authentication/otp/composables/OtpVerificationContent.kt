package com.pregnancy.edu.feature.authentication.otp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItemDefaults.containerColor
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pregnancy.edu.feature.authentication.otp.event.OtpEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OtpVerificationContent(
    codeLength: Int = 6,
    onTriggerEvent: (OtpEvent) -> Unit = { },
    email: String,
    isLoading: Boolean = false,
    error: String? = null
) {
    val otpValues = remember { List(codeLength) { mutableStateOf("") } }
    val focusRequesters = remember { List(codeLength) { FocusRequester() } }
    var isVerifying by remember { mutableStateOf(false) }
    var resendEnabled by remember { mutableStateOf(false) }
    var countdown by remember { mutableStateOf(30) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        // Auto-focus on the first OTP field
        focusRequesters[0].requestFocus()

        // Start countdown for resend button
        scope.launch {
            while (countdown > 0) {
                delay(1000)
                countdown--
            }
            resendEnabled = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Verification Code",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black,
        )

        Text(
            text = "We have sent the verification code to your email",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // OTP Input Fields
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            otpValues.forEachIndexed { index, otpValue ->
                // Add a state to track if the field was just emptied
                val wasJustEmptied = remember { mutableStateOf(false) }

                OtpTextField(
                    otpValue = otpValue.value,
                    onOtpValueChange = { value ->
                        if (value.length <= 1) {
                            val previousValue = otpValue.value
                            otpValue.value = value

                            // Check if the field was just emptied (backspace was pressed)
                            if (previousValue.isNotEmpty() && value.isEmpty()) {
                                wasJustEmptied.value = true
                            } else if (value.isNotEmpty() && previousValue.isEmpty() && index < codeLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            }

                            onTriggerEvent(OtpEvent.ErrorDismissed)
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .focusRequester(focusRequesters[index])
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.type == KeyEventType.KeyUp && keyEvent.key == Key.Backspace) {
                                if (otpValue.value.isEmpty()) {
                                    if (!wasJustEmptied.value && index > 0) {
                                        // Only move focus if the field was already empty (not just emptied)
                                        focusRequesters[index - 1].requestFocus()
                                        otpValues[index - 1].value = ""
                                        return@onKeyEvent true
                                    }
                                    // Reset the flag
                                    wasJustEmptied.value = false
                                }
                            }
                            false
                        }
                )
            }
        }

        // Error message
        error?.let { errorMessage ->
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            onClick = {
                val otp = otpValues.joinToString("") { it.value }
                onTriggerEvent(OtpEvent.ValidateEmail(email, otp))
            },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFAACAA),
                contentColor = Color.White,
                disabledContainerColor = Color.LightGray,
                disabledContentColor = Color.Gray
            )
        ) {
            if (isVerifying) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Verify",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }

        // Resend code option
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Didn't receive the code? ")
            if (resendEnabled) {
                TextButton(
                    onClick = {
                        // Reset countdown and disable resend button
                        resendEnabled = false
                        countdown = 30

                        // Clear all input fields
                        otpValues.forEach { it.value = "" }

                        // Focus on the first field
                        focusRequesters[0].requestFocus()

                        // Start countdown again
                        scope.launch {
                            while (countdown > 0) {
                                delay(1000)
                                countdown--
                            }
                            resendEnabled = true
                        }
                    },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Resend Code",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                Text(
                    text = "Resend in ${countdown}s",
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

// Sample usage in a Composable function
@Preview
@Composable
fun OtpVerificationScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        OtpVerificationContent(email = "")
    }
}