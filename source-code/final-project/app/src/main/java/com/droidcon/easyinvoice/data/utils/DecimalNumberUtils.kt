package com.droidcon.easyinvoice.data.utils

import java.math.RoundingMode
import java.text.DecimalFormat

fun roundDouble(number: Double): Double {
    val decimalNumber = DecimalFormat("#.##")
    decimalNumber.roundingMode = RoundingMode.CEILING
    return decimalNumber.format(number).toDouble()
}