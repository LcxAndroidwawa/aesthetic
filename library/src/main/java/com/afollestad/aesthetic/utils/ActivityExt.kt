/*
 * Licensed under Apache-2.0
 *
 * Designed and developed by Aidan Follestad (@afollestad)
 */
package com.afollestad.aesthetic.utils

import android.annotation.TargetApi
import android.app.Activity
import android.app.ActivityManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Build.VERSION_CODES.M
import android.os.Build.VERSION_CODES.O
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat.setFactory2
import com.afollestad.aesthetic.internal.InflationDelegate
import com.afollestad.aesthetic.internal.InflationInterceptor

internal fun AppCompatActivity.setInflaterFactory(
  li: LayoutInflater,
  aestheticDelegate: InflationDelegate?
) = setFactory2(li, InflationInterceptor(this, aestheticDelegate, delegate))

internal fun Activity.setStatusBarColorCompat(@ColorInt color: Int) {
  if (SDK_INT >= LOLLIPOP) {
    window.statusBarColor = color
  }
}

internal fun Activity.getRootView(): ViewGroup {
  val content = findViewById<ViewGroup>(android.R.id.content)
  return content.getChildAt(0) as ViewGroup
}

internal fun Activity?.setNavBarColorCompat(@ColorInt color: Int) {
  if (SDK_INT >= LOLLIPOP) {
    this?.window?.navigationBarColor = color
  }
}

internal fun Activity?.setLightStatusBarCompat(lightMode: Boolean) {
  val view = this?.window?.decorView ?: return
  if (SDK_INT >= M) {
    var flags = view.systemUiVisibility
    flags = if (lightMode) {
      flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
      flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    }
    view.systemUiVisibility = flags
  }
}

internal fun Activity?.setLightNavBarCompat(lightMode: Boolean) {
  val view = this?.window?.decorView ?: return
  if (SDK_INT >= O) {
    var flags = view.systemUiVisibility
    flags = if (lightMode) {
      flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    } else {
      flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
    }
    view.systemUiVisibility = flags
  }
}

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
internal fun Activity?.setTaskDescriptionColor(@ColorInt requestedColor: Int) {
  if (this == null || SDK_INT <= LOLLIPOP) return
  var color = requestedColor

  // Task description requires fully opaque color
  color = color.stripAlpha()
  // Default is app's launcher icon
  val icon: Bitmap? = if (Build.VERSION.SDK_INT >= 26) {
    packageManager.getAppIcon(packageName)
  } else {
    (applicationInfo.loadIcon(packageManager) as BitmapDrawable)
        .bitmap
  }
  if (icon != null) {
    // Sets color of entry in the system recents page
    @Suppress("DEPRECATION")
    val td = ActivityManager.TaskDescription(title as String, icon, color)
    setTaskDescription(td)
  }
}
