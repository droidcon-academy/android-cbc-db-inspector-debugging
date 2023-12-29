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

package com.droidcon.easyinvoice.ui.home.nav

import android.content.res.Configuration
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.droidcon.easyinvoice.ui.AppScreen
import com.droidcon.easyinvoice.ui.drawer.Drawer
import com.droidcon.easyinvoice.ui.home.TopBar
import com.droidcon.easyinvoice.ui.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun HomeNavHost() {
    val title = remember { mutableIntStateOf(AppScreen.Invoices.title) }
    val navController = rememberNavController()

    Surface(color = MaterialTheme.colorScheme.background) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                Drawer(
                    navController = navController,
                    onDestinationClicked = { route ->
                        scope.launch { drawerState.close() }
                        navController.navigate(route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        ) {
            TopBar(
                title = title.intValue,
                onButtonClicked = {
                    scope.launch {
                        drawerState.open()
                    }
                }, content = {
                    NavHost(
                        navController = navController,
                        startDestination = AppScreen.Invoices.route
                    ) {
                        invoiceNav(navController)
                        taxNav(navController)
                        businessNav(navController)
                        customersNav(navController)
                    }
                }
            )
        }
    }

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            title.intValue = backStackEntry.getTitle()
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AppMainScreenPreviewLight() {
    AppTheme {
        HomeNavHost()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AppMainScreenPreviewDark() {
    AppTheme {
        HomeNavHost()
    }
}