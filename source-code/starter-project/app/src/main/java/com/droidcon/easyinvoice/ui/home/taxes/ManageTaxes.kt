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

package com.droidcon.easyinvoice.ui.home.taxes

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.droidcon.easyinvoice.R
import com.droidcon.easyinvoice.ui.commons.UserConfirmationDialog
import com.droidcon.easyinvoice.ui.faker.FakeViewModelProvider
import com.droidcon.easyinvoice.ui.theme.AppTheme
import com.droidcon.easyinvoice.ui.theme.spacing
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ManageTaxes(viewModel: TaxesViewModel, navController: NavController) {

    val showDeleteConfirmation = remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = BringIntoViewRequester()

    val desc = viewModel.desc.collectAsState()
    val value = viewModel.value.collectAsState()
    val areInputsValid = viewModel.areInputsValid.collectAsState()
    val isUpdating = viewModel.isUpdating.collectAsState()

    val spacing = spacing

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = spacing.xLarge,
                end = spacing.xLarge,
                top = spacing.medium,
                bottom = spacing.medium
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.add_tax),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = spacing.large, bottom = spacing.large)
        )

        TextField(
            value = desc.value,
            onValueChange = {
                viewModel.desc.value = it
            },
            label = {
                Text(text = stringResource(id = R.string.desc))
            },
            modifier = Modifier
                .padding(bottom = spacing.medium)
                .fillMaxWidth()
                .onFocusEvent {
                    if (it.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
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
        )

        TextField(
            value = value.value,
            onValueChange = {
                viewModel.value.value = it
            },
            label = {
                Text(text = stringResource(id = R.string.tax_value))
            },
            modifier = Modifier
                .padding(bottom = spacing.medium)
                .fillMaxWidth()
                .onFocusEvent {
                    if (it.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.validateInputs()
                    focusManager.clearFocus()
                },
            ),
            maxLines = 3,
        )

        Button(
            onClick = {
                viewModel.addUpdateTax()
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = spacing.xLarge, end = spacing.xLarge)
                .bringIntoViewRequester(bringIntoViewRequester),
            enabled = areInputsValid.value
        ) {
            Text(
                text = if (isUpdating.value == null) stringResource(id = R.string.add) else stringResource(id = R.string.update),
                style = MaterialTheme.typography.titleMedium
            )
        }

        if (isUpdating.value != null) {
            Button(
                onClick = {
                    showDeleteConfirmation.value = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = spacing.xLarge, top = spacing.medium, end = spacing.xLarge),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(
                    text = stringResource(id = R.string.delete),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onError
                )
            }
        }
    }

    if (showDeleteConfirmation.value) {
        UserConfirmationDialog { confirmation ->
            if (confirmation) {
                viewModel.deleteTax()
                navController.popBackStack()
            }
            showDeleteConfirmation.value = false
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ManageTaxesPreviewLight() {
    AppTheme {
        ManageTaxes(FakeViewModelProvider.provideTaxesViewModel(), rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ManageTaxesPreviewDark() {
    AppTheme {
        ManageTaxes(FakeViewModelProvider.provideTaxesViewModel(), rememberNavController())
    }
}