package com.pregnancy.edu.common.base.permission

import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.pregnancy.edu.common.base.permission.PermissionState
import com.pregnancy.edu.common.base.permission.PermissionStateHolder
import com.pregnancy.edu.common.base.permission.Permissions


/**
 * Remembers the permission state for a given permission.
 * Example usage:
 *
 * ```kotlin
 * val permissionState = rememberPermissionState(permission = Permissions.CAMERA)
 * when (permissionState.state) {
 *     PermissionState.Denied -> ShowPermissionDenied()
 *     PermissionState.ShowRationale -> ShowRationaleDialog()
 *     PermissionState.Granted -> ShowCameraPreview()
 *     PermissionState.Initial -> LoadingState()
 * }
 * ```
 *
 * This function allows you to handle various states of permissions:
 * - **Denied**: Permission was denied, you can show a message to the user or request again.
 * - **ShowRationale**: Permission needs a rationale before requesting.
 * - **Granted**: Permission is granted, proceed to access the resource.
 * @see com.fpl.base.permission.PermissionStateHolder
 * @see com.fpl.base.permission.PermissionState
 * @see com.fpl.base.permission.Permissions
 */
@Composable
fun rememberPermissionState(
    permission: String,
    onPermissionResult: (PermissionState) -> Unit = {}
): PermissionStateHolder {
    val context = LocalContext.current
    val permissionState = remember(permission) {
        PermissionStateHolder(context, permission)
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        val newState = when {
            isGranted -> PermissionState.Granted
            permissionState.shouldShowRationale -> PermissionState.ShowRationale
            else -> PermissionState.Denied
        }
        onPermissionResult(newState)
        permissionState.updatePermissionState()
    }

    DisposableEffect(permissionState) {
        permissionState.setLauncher { shouldLaunch ->
            if (!shouldLaunch) {
                if (permissionRequiresApiCheck(permission)) {
                    launcher.launch(permission)
                }
            }
        }
        onDispose {
            permissionState.setLauncher(null)
        }
    }

    return permissionState
}

private fun permissionRequiresApiCheck(permission: String): Boolean {
    return when (permission) {
        Permissions.READ_MEDIA_IMAGES,
        Permissions.POST_NOTIFICATIONS -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) true
            else {
                Log.e("Permission", "Permission $permission requires API level ${Build.VERSION_CODES.TIRAMISU} or higher")
                false
            }
        }

        else -> true
    }
}