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

import android.app.Activity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.droidcon.easyinvoice.ui.AppScreen
import com.droidcon.easyinvoice.ui.home.HomeActivity
import com.droidcon.easyinvoice.ui.home.invoices.InvoiceItemViewModel
import com.droidcon.easyinvoice.ui.home.invoices.InvoicesScreen
import com.droidcon.easyinvoice.ui.home.invoices.InvoicesViewModel
import com.droidcon.easyinvoice.ui.home.invoices.detail.InvoiceDetail
import com.droidcon.easyinvoice.ui.home.invoices.manage.AddInvoiceItem
import com.droidcon.easyinvoice.ui.home.invoices.manage.PickBusinessScreen
import com.droidcon.easyinvoice.ui.home.invoices.manage.PickCustomerScreen
import com.droidcon.easyinvoice.ui.home.invoices.manage.PickTaxScreen
import com.droidcon.easyinvoice.ui.utils.getViewModelInstance
import dagger.hilt.android.EntryPointAccessors


fun NavGraphBuilder.invoiceNav(navController: NavController) {
    navigation(
        startDestination = AppScreen.Invoices.Home.route,
        route = AppScreen.Invoices.route
    ) {
        composable(AppScreen.Invoices.Home.route) {
            val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
            InvoicesScreen(vm, navController)
        }

        composable(AppScreen.Invoices.InvoiceDetail.route) {
            val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
            InvoiceDetail(vm)
        }

        navigation(
            startDestination = AppScreen.Invoices.ManageInvoice.PickBusiness.route,
            route = AppScreen.Invoices.ManageInvoice.route
        ) {

            composable(AppScreen.Invoices.ManageInvoice.PickBusiness.route) {
                val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
                PickBusinessScreen(vm, navController)
            }

            composable(AppScreen.Invoices.ManageInvoice.PickCustomer.route) {
                val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
                PickCustomerScreen(vm, navController)
            }

            composable(AppScreen.Invoices.ManageInvoice.PickTax.route) {
                val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
                PickTaxScreen(vm, navController)
            }

            composable(AppScreen.Invoices.ManageInvoice.AddItems.route) {
                val invoiceViewModel = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
                val invoiceId = invoiceViewModel.invoiceId.collectAsState()
                val factory = EntryPointAccessors.fromActivity(
                    LocalContext.current as Activity,
                    HomeActivity.ViewModelFactoryProvider::class.java
                ).invoiceItemViewModelFactory()

                invoiceId.value?.let { id ->
                    val invoiceItemViewModel: InvoiceItemViewModel = viewModel(factory = InvoiceItemViewModel.provideInvoiceItemViewModelFactory(factory, id.toInt()))
                    AddInvoiceItem(invoiceItemViewModel, navController)
                }
            }
        }
    }
}