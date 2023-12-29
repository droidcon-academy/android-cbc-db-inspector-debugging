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

package com.droidcon.easyinvoice.ui.drawer

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.droidcon.easyinvoice.ui.theme.AppTheme
import com.droidcon.easyinvoice.ui.AppScreen
import com.droidcon.easyinvoice.R
import com.droidcon.easyinvoice.ui.theme.spacing

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    navController: NavController,
    onDestinationClicked: (route: String) -> Unit
) {
    val screens = listOf(
        AppScreen.Invoices,
        AppScreen.Taxes,
        AppScreen.MyBusinesses,
        AppScreen.Customers
    )

    val spacing = spacing
    Column(
        modifier = modifier
            .width(spacing.drawerWidth)
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(start = spacing.drawerItemPadding, end = spacing.drawerItemPadding, top = spacing.xLarge)
    ) {

        Image(
            modifier = Modifier
                .padding(spacing.drawerIconPadding)
                .size(spacing.xLarge),
            painter = painterResource(R.drawable.ic_app_logo),
            contentDescription = stringResource(id = R.string.app_name)
        )

        screens.forEach { screen ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(spacing.drawerItemHeight)
                    .padding(top = spacing.medium)
                    .clip(RoundedCornerShape(spacing.drawerCornerRadius))
                    .also {
                        val background = if (navController.currentDestination?.route == screen.route) {
                            MaterialTheme.colorScheme.tertiaryContainer
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                        it.background(background)
                    }
                    .clickable {
                        onDestinationClicked.invoke(screen.route)
                    },
                verticalAlignment = CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(start = spacing.drawerIconPadding, end = spacing.drawerIconPadding)
                        .size(spacing.drawerIconSize),
                    painter = painterResource(id = screen.icon),
                    contentDescription = stringResource(id = screen.title),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )

                Text(
                    text = stringResource(id = screen.title),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DrawerPreviewLight() {
    AppTheme {
        Drawer(navController = rememberNavController(), onDestinationClicked = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DrawerPreviewDark() {
    AppTheme {
        Drawer(navController = rememberNavController(), onDestinationClicked = {})
    }
}