package com.pregnancy.edu.feature.profile

import androidx.lifecycle.viewModelScope
import com.pregnancy.data.source.local.TokenManager
import com.pregnancy.domain.model.authentication.User
import com.pregnancy.domain.usecase.auth.GetMyProfileUseCase
import com.pregnancy.edu.common.base.interfaces.ViewEvent
import com.pregnancy.edu.common.base.interfaces.ViewModelState
import com.pregnancy.edu.common.base.interfaces.ViewState
import com.pregnancy.edu.common.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val tokenManager: TokenManager
) : BaseViewModel<ProfileViewModel.ProfileEvent, ProfileViewModel.ProfileState, ProfileViewModel.ProfileViewModelState>(
    initState = ProfileViewModelState()
) {
    init {
        loadMyProfile()
    }

    override fun onTriggerEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.LoadMyProfile -> loadMyProfile()
            ProfileEvent.Logout -> logout()
        }
    }

    private fun loadMyProfile() {
        viewModelState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            val result = getMyProfileUseCase()
            result.onSuccess { user ->
                viewModelState.update { it.copy(user = user, isLoading = false, error = null) }
            }.onFailure { error ->
                viewModelState.update {
                    it.copy(
                        isLoading = false,
                        error = error.localizedMessage ?: "Unknown error"
                    )
                }
            }
        }
    }
    // This would be added to ProfileViewModel.kt
    private fun logout() {
        viewModelScope.launch {
            try {
                // Clear auth tokens
                tokenManager.clearTokens()

                // Update UI state if needed
                viewModelState.update { currentState ->
                    currentState.copy(
                        user = null,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                // Handle any errors during logout
                viewModelState.update { currentState ->
                    currentState.copy(
                        error = e.message ?: "Logout failed",
                        isLoading = false
                    )
                }
            }
        }
    }

    override fun <T> Result<T>.reduce(event: ProfileViewModel.ProfileEvent) {
        TODO("Not yet implemented")
    }

    sealed class ProfileEvent : ViewEvent {
        data object LoadMyProfile : ProfileEvent()
        data object Logout : ProfileEvent()
    }

    data class ProfileState(
        val user: User? = null,
        val isLoading: Boolean = false,
        val error: String? = null,
    ) : ViewState()

    data class ProfileViewModelState(
        val user: User? = null,
        val isLoading: Boolean = false,
        val error: String? = null,
    ) : ViewModelState() {
        override fun toUiState(): ViewState {
            return ProfileState(
                user = user,
                isLoading = isLoading,
                error = error
            )
        }
    }
}