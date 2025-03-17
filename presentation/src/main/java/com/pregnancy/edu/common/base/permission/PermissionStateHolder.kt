package com.pregnancy.edu.common.base.permission

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@Stable
class PermissionStateHolder(
    private val context: Context,
    private val permission: String,
) {
    // States
    private var _state: PermissionState by mutableStateOf(initializePermissionState())
    val state: PermissionState get() = _state

    private var _shouldShowRationale by mutableStateOf(false)
    val shouldShowRationale: Boolean get() = _shouldShowRationale

    private var launcher: ((Boolean) -> Unit)? = null

    init {
        updatePermissionState()
    }

    fun setLauncher(onResult: ((Boolean) -> Unit)?) {
        launcher = onResult
    }

    fun requestPermission() {
        when {
            isPermissionGranted() -> {
                _state = PermissionState.Granted
                launcher?.invoke(true)
            }
            _shouldShowRationale -> {
                _state = PermissionState.ShowRationale
            }
            else -> {
                launcher?.invoke(false) // Launch permission request
            }
        }
    }

    internal fun updatePermissionState() {
        context.findActivity()?.let { activity ->
            _shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                permission
            )
            _state = when {
                isPermissionGranted() -> PermissionState.Granted
                _shouldShowRationale -> PermissionState.ShowRationale
                else -> PermissionState.Denied
            }
        }
    }

    private fun initializePermissionState(): PermissionState {
        return if (isPermissionGranted()) {
            PermissionState.Granted
        } else {
            PermissionState.Denied
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun Context.findActivity(): Activity? {
        var context = this
        while (context is ContextWrapper) {
            if (context is Activity) return context
            context = context.baseContext
        }
        return null
    }
}