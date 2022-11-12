package com.yingenus.core.ImageUtils

import android.widget.ImageButton

fun ImageButton.isChecked( bol : Boolean){
    if (bol)setImageState(intArrayOf(android.R.attr.state_checked),true)
    else setImageState(intArrayOf(),true)
}
fun ImageButton.isChecked(): Boolean{
    return drawableState.any { it ==  android.R.attr.state_checked}
}