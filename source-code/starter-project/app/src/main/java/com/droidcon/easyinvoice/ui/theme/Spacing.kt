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

package com.droidcon.easyinvoice.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
    val xLarge: Dp = 64.dp,
    val xxLarge: Dp = 96.dp,
    val xxxLarge: Dp = 128.dp,

    val drawerWidth: Dp = 360.dp,
    val drawerIconSize: Dp = 24.dp,
    val drawerItemHeight: Dp = 56.dp,
    val drawerLeftPadding: Dp = 28.dp,
    val drawerRightPadding: Dp = 28.dp,
    val drawerItemPadding: Dp = 12.dp,
    val drawerCornerRadius: Dp = 28.dp,
    val drawerIconPadding: Dp = 16.dp,

    val invoiceRowHeight: Dp = 52.dp
)


val LocalSpacing = compositionLocalOf { Spacing() }

val spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

