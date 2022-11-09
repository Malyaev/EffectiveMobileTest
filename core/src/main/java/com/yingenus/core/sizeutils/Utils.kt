package com.yingenus.core.sizeutils

import android.content.Context
import android.util.DisplayMetrics

fun Int.dp2px( context: Context): Int{
    val dm = context.resources.displayMetrics
    return Math.round(this * (dm.xdpi/ DisplayMetrics.DENSITY_DEFAULT))
}