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

package com.droidcon.easyinvoice.ui.home.invoices

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.droidcon.easyinvoice.R
import com.droidcon.easyinvoice.data.entities.InvoiceWithItems
import com.droidcon.easyinvoice.ui.AppScreen
import com.droidcon.easyinvoice.ui.commons.EmptyScreen
import com.droidcon.easyinvoice.ui.commons.UserConfirmationDialog
import com.droidcon.easyinvoice.ui.theme.AppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InvoicesScreen(viewModel: InvoicesViewModel, navController: NavController) {

    val invoices = viewModel.invoices.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AppScreen.Invoices.ManageInvoice.route)
                }
            ) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.empty))
            }
        },
        content = {
            if (invoices.value.isEmpty()) {
                EmptyScreen(title = stringResource(id = R.string.empty_invoice)) { }
            } else {
                Invoices(
                    invoices = invoices.value,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    )
}

@Composable
fun Invoices(invoices: List<InvoiceWithItems>, viewModel: InvoicesViewModel, navController: NavController) {

    val invoiceDeleteConfirmation = remember { mutableStateOf<Int?>(null) }

    LazyColumn {
        items(invoices) { item ->
            InvoiceCard(
                invoice = item,
                onClick = {
                    viewModel.currentInvoice.value = item
                    navController.navigate(AppScreen.Invoices.InvoiceDetail.route)
                },
                onMenuClick = {
                    when (it) {
                        InvoiceMenu.Delete -> {
                            invoiceDeleteConfirmation.value = item.invoice.id
                        }
                        InvoiceMenu.Edit -> {
                            viewModel.setUpdating(item.invoice)
                            navController.navigate(AppScreen.Invoices.ManageInvoice.route)
                        }
                        InvoiceMenu.MarkAsPaid -> {
                            viewModel.setPaidStatus(item.invoice.id!!, true)
                        }
                        InvoiceMenu.MarkAsUnPaid -> {
                            viewModel.setPaidStatus(item.invoice.id!!, false)
                        }
                    }
                }
            )
        }
    }

    if (invoiceDeleteConfirmation.value != null) {
        UserConfirmationDialog { confirmation ->
            if (confirmation) {
                viewModel.deleteInvoice(invoiceDeleteConfirmation.value)
            }
            invoiceDeleteConfirmation.value = null
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun InvoicesPreviewLight() {
    AppTheme {

    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun InvoicesPreviewDark() {
    AppTheme {

    }
}