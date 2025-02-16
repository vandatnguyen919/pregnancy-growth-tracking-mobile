package com.pregnancy.edu.common.base.interfaces

sealed interface KeyboardAction {
    data class Next(val onClick: () -> Unit) : KeyboardAction
    data class Done(val onClick: () -> Unit) : KeyboardAction
    data object None : KeyboardAction
}