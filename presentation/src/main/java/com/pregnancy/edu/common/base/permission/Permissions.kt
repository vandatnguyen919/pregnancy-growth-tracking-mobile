package com.pregnancy.edu.common.base.permission

import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Contains Permission that is being used by the app
 */
object Permissions {
    const val CAMERA = android.Manifest.permission.CAMERA
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    const val READ_MEDIA_IMAGES = android.Manifest.permission.READ_MEDIA_IMAGES
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    const val POST_NOTIFICATIONS = android.Manifest.permission.POST_NOTIFICATIONS

    const val READ_EXTERNAL_STORAGE = android.Manifest.permission.READ_EXTERNAL_STORAGE
}