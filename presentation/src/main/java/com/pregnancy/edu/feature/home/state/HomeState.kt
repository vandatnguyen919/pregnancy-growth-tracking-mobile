package com.pregnancy.edu.feature.home.state

import com.pregnancy.domain.model.pregnancy.Pregnancy
import com.pregnancy.edu.common.base.interfaces.ViewState
import java.time.LocalDate

data class HomeState(
    val pregnancy: Pregnancy? = null,
    val expectedDueDate: LocalDate? = null,
    val currentDate: LocalDate? = null,
    val weeksPregnant: Int = 0,
    val trimester: String = "",
    val remainingDays: Int = 0,
    val gestationalWeekInsight: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
) : ViewState() {
}