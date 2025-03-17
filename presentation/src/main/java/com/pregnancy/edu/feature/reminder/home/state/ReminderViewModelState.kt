package com.pregnancy.edu.feature.reminder.home.state

import androidx.paging.PagingData
import com.pregnancy.domain.model.reminder.Reminder
import com.pregnancy.edu.common.base.interfaces.ViewModelState
import com.pregnancy.edu.common.base.interfaces.ViewState

data class ReminderViewModelState(
    val pagingData: PagingData<Reminder> = PagingData.empty(),
    val isLoading: Boolean = false,
    val error: String? = null
) : ViewModelState() {

    override fun toUiState(): ViewState {
        return ReminderState(
            pagingData = pagingData,
            isLoading = isLoading,
            error = error
        )
    }
}