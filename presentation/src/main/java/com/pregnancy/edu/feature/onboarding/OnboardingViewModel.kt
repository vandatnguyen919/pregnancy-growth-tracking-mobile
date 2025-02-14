package com.pregnancy.edu.feature.onboarding

import androidx.lifecycle.ViewModel
import com.pregnancy.edu.R
import com.pregnancy.edu.feature.onboarding.models.OnboardingPage
import kotlinx.coroutines.flow.MutableStateFlow

class OnboardingViewModel : ViewModel() {

    val uiState = MutableStateFlow(
        listOf(
            OnboardingPage(
                title = "Welcome to PregnaJoy",
                description = "Your trusted companion throughout your pregnancy journey",
                imageRes = R.drawable.pregnancy_woman // Replace with your actual image resource
            ),
            OnboardingPage(
                title = "Track Your Progress",
                description = "Monitor your pregnancy week by week with detailed insights",
                imageRes = R.drawable.checklist // Replace with your actual image resource
            ),
            OnboardingPage(
                title = "Expert Guidance",
                description = "Access reliable information from healthcare professionals",
                imageRes = R.drawable.baby // Replace with your actual image resource
            )
        )
    )
}