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

package com.droidcon.easyinvoice.ui.home.invoices.manage

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.droidcon.easyinvoice.R
import com.droidcon.easyinvoice.data.entities.InvoiceItem
import com.droidcon.easyinvoice.ui.AppScreen
import com.droidcon.easyinvoice.ui.home.invoices.InvoiceItemViewModel
import com.droidcon.easyinvoice.ui.theme.AppTheme
import com.droidcon.easyinvoice.ui.theme.spacing

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddInvoiceItem(viewModel: InvoiceItemViewModel, navController: NavController) {
    val spacing = spacing
    val invoiceItems = viewModel.invoiceItems.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AppScreen.Invoices.route) {
                        popUpTo(AppScreen.Invoices.ManageInvoice.route) { inclusive = true }
                    }
                },
            ) {
                Icon(Icons.Filled.Done, stringResource(id = R.string.empty))
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacing.medium)
            ) {
                Text(
                    text = stringResource(id = R.string.add_invoice_items),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = spacing.medium)
                )

                InvoiceItemInput(viewModel)

                Divider()

                Text(
                    text = stringResource(id = R.string.invoice),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = spacing.extraSmall, bottom = spacing.extraSmall)
                )

                Divider(modifier = Modifier.padding(bottom = spacing.medium))

                LazyColumn {
                    items(invoiceItems.value) { item ->
                        InvoiceItemCard(viewModel, item)
                    }
                }
            }
        }
    )
}

@Composable
fun InvoiceItemInput(viewModel: InvoiceItemViewModel) {
    val focusManager = LocalFocusManager.current
    val spacing = spacing
    val desc = viewModel.desc.collectAsState()
    val qty = viewModel.qty.collectAsState()
    val price = viewModel.price.collectAsState()
    val isUpdating = viewModel.isUpdating.collectAsState()
    val areInputsValid = viewModel.areInputsValid.collectAsState()

    Column(
        modifier = Modifier.wrapContentHeight()
    ) {
        Row {
            TextField(
                value = desc.value,
                onValueChange = {
                    viewModel.desc.value = it
                },
                label = { Text(text = stringResource(id = R.string.desc)) },
                modifier = Modifier
                    .weight(2f)
                    .padding(end = spacing.small),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        viewModel.validateInputs()
                        focusManager.moveFocus(FocusDirection.Next)
                    },
                ),
                maxLines = 1,
                singleLine = true,
            )

            TextField(
                value = qty.value,
                onValueChange = {
                    viewModel.qty.value = it
                },
                label = { Text(text = stringResource(id = R.string.qty)) },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        viewModel.validateInputs()
                        focusManager.moveFocus(FocusDirection.Next)
                    },
                ),
                maxLines = 1,
                singleLine = true,
            )

            TextField(
                value = price.value,
                onValueChange = {
                    viewModel.price.value = it
                },
                label = { Text(text = stringResource(id = R.string.price)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = spacing.small),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.validateInputs()
                        focusManager.clearFocus()
                    },
                ),
                maxLines = 1,
                singleLine = true,
            )
        }
        TextButton(
            onClick = {
                viewModel.addUpdateInvoiceItem()
                focusManager.clearFocus()
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = spacing.medium),
            enabled = areInputsValid.value
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.empty),
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                text = if (isUpdating.value == null) stringResource(id = R.string.add) else stringResource(id = R.string.update),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun InvoiceItemCard(viewModel: InvoiceItemViewModel, invoiceItem: InvoiceItem) {
    val spacing = spacing
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = spacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = invoiceItem.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .weight(0.5f)
                .padding(end = spacing.medium)
        )

        Text(
            text = invoiceItem.quantity.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.1f)
        )

        Text(
            text = invoiceItem.price.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End,
            modifier = Modifier
                .weight(0.2f)
                .padding(start = spacing.medium, end = spacing.extraSmall)
        )

        IconButton(
            onClick = {
                viewModel.setUpdating(invoiceItem)
            },
            modifier = Modifier.weight(0.1f)
        ) {
            Image(
                imageVector = Icons.Filled.Edit,
                contentDescription = stringResource(id = R.string.empty),
                modifier = Modifier.size(ButtonDefaults.IconSize),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
            )
        }

        IconButton(
            onClick = {
                viewModel.deleteInvoiceItem(invoiceItem.id)
            },
            modifier = Modifier.weight(0.1f)
        ) {
            Image(
                imageVector = Icons.Filled.Clear,
                contentDescription = stringResource(id = R.string.empty),
                modifier = Modifier.size(ButtonDefaults.IconSize),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error)
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewAddInvoiceItemLight() {
    AppTheme {
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAddInvoiceItemDark() {
    AppTheme {
    }
}