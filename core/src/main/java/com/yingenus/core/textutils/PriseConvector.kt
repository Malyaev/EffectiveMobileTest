package com.yingenus.core.textutils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun Int.convertPrise(
    currencyString: String = "",
    afterNullDigs: Int = 0
): String{
    return currencyString+
            DecimalFormat("###,###,###"
                .let {
                    if (afterNullDigs == 0) it
                    else StringBuilder(it)
                        .append(".").also{ sb : StringBuilder -> repeat(afterNullDigs){ sb.append(".")} }.toString()
                })
                .format(this)
}

fun Int.convertPrise(
    currencyString: String = "",
    afterNullDigs: Int = 0,
    currencyShot : String = ""
): String{
    return convertPrise(currencyString,afterNullDigs).let {
        if (currencyShot.isNotEmpty()) it +" "+ currencyShot
        else it
    }
}