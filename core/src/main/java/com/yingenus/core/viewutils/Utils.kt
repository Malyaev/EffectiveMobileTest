package com.yingenus.core.viewutils

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.reflect.KClass


fun <T : View> View.findView( clazz: Class<T>): T?{
    if ( clazz.isAssignableFrom(this::class.java)) return this as T
    else if (this is ViewGroup){
        this.children.forEach {
            val view = it.findView(clazz)
            if (view != null) return view
        }
    }
    return null
}