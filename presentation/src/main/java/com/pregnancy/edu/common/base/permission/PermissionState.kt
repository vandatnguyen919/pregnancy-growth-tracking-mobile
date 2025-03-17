package com.pregnancy.edu.common.base.permission

/**
 * Represents the current state of a permission
 */
sealed class PermissionState {
    data object Granted : PermissionState()
    data object Denied : PermissionState()
    data object ShowRationale : PermissionState()
}
