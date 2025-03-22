package com.pregnancy.edu.feature.home.event

import com.pregnancy.edu.common.base.interfaces.ViewEvent

sealed class HomeEvent : ViewEvent {

    data object LoadMyOnGoingPregnancy : HomeEvent()

    data object LoadGestationalWeekInsight : HomeEvent()
}