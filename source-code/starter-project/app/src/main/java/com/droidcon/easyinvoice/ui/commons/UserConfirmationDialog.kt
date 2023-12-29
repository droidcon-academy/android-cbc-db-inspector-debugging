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

package com.droidcon.easyinvoice.ui.commons

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.droidcon.easyinvoice.R

@Composable
fun UserConfirmationDialog(
    onComplete: (shallDelete: Boolean) -> Unit
) {
    AlertDialog(
        onDismissRequest = { onComplete.invoke(false) },
        confirmButton = {
            TextButton(
                onClick = { onComplete.invoke(true) },
                content = {
                    Text(
                        text = stringResource(id = R.string.ok),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            )

        },
        dismissButton = {
            TextButton(
                onClick = { onComplete.invoke(false) },
                content = {
                    Text(
                        text = stringResource(id = R.string.cancel)
                    )
                }
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.delete_confirmation),
                style = MaterialTheme.typography.titleMedium
            )
        }
    )
}