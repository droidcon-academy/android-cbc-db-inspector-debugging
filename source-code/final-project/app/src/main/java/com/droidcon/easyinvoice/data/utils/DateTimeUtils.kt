/*
 * Copyright (c) 2024. Droidcon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.droidcon.easyinvoice.data.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

val currentDateTime: Long = Calendar.getInstance().timeInMillis

fun toDateString(time: Long): String {
    return DateUtils.getRelativeTimeSpanString(
        time,
        Date().time,
        DateUtils.MINUTE_IN_MILLIS,
        DateUtils.FORMAT_ABBREV_RELATIVE
    ).toString()
}


fun Long?.toDateTime(): String {
    if (this != null) {
        val date = Date(this)
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
        return format.format(date)
    }
    return ""
}