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

package com.droidcon.easyinvoice.ui.home.mybusinesses

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
import androidx.compose.ui.unit.dp
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
fun ManageMyBusiness(viewModel: MyBusinessViewModel, navController: NavController) {

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = BringIntoViewRequester()
    val spacing = spacing

    val name = viewModel.name.collectAsState()
    val address = viewModel.address.collectAsState()
    val phone = viewModel.phone.collectAsState()
    val email = viewModel.email.collectAsState()
    val areInputsValid = viewModel.areInputsValid.collectAsState()
    val isUpdating = viewModel.isUpdating.collectAsState()
    val showConfirmDeletion = remember { mutableStateOf(false) }

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
            text = stringResource(id = R.string.add_business),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = spacing.large, bottom = spacing.large)
        )

        TextField(
            value = name.value,
            onValueChange = {
                viewModel.name.value = it
            },
            label = {
                Text(text = stringResource(id = R.string.name))
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
            maxLines = 1
        )

        TextField(
            value = address.value,
            onValueChange = {
                viewModel.address.value = it
            },
            label = {
                Text(text = stringResource(id = R.string.address))
            },
            modifier = Modifier
                .padding(bottom = spacing.medium)
                .fillMaxWidth()
                .height(110.dp)
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
            maxLines = 3
        )

        TextField(
            value = phone.value,
            onValueChange = {
                viewModel.phone.value = it
            },
            label = {
                Text(text = stringResource(id = R.string.phone))
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
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    viewModel.validateInputs()
                    focusManager.moveFocus(FocusDirection.Next)
                },
            ),
            maxLines = 1
        )

        TextField(
            value = email.value,
            onValueChange = {
                viewModel.email.value = it
            },
            label = {
                Text(text = stringResource(id = R.string.email))
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
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.validateInputs()
                    focusManager.clearFocus()
                }
            ),
            maxLines = 1
        )

        Button(
            onClick = {
                viewModel.addUpdateBusiness()
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
                    showConfirmDeletion.value = true
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

    if (showConfirmDeletion.value) {
        UserConfirmationDialog { confirmation ->
            if (confirmation) {
                viewModel.deleteBusiness()
                navController.popBackStack()
            }
            showConfirmDeletion.value = false
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AddMyBusinessesPreviewLight() {
    AppTheme {
        ManageMyBusiness(FakeViewModelProvider.provideMyBusinessViewModel(), rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddMyBusinessesPreviewDark() {
    AppTheme {
        ManageMyBusiness(FakeViewModelProvider.provideMyBusinessViewModel(), rememberNavController())
    }
}
