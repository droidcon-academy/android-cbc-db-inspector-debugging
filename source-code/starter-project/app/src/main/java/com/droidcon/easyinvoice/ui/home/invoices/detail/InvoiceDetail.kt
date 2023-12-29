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

package com.droidcon.easyinvoice.ui.home.invoices.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.droidcon.easyinvoice.ui.theme.AppTheme
import com.droidcon.easyinvoice.R
import com.droidcon.easyinvoice.data.utils.toDateTime
import com.droidcon.easyinvoice.ui.faker.FakeViewModelProvider
import com.droidcon.easyinvoice.ui.home.invoices.InvoicesViewModel
import com.droidcon.easyinvoice.ui.theme.spacing
import com.droidcon.easyinvoice.ui.utils.toStringOrEmpty

@Composable
fun InvoiceDetail(viewModel: InvoicesViewModel) {
    val spacing = spacing
    val invoice = viewModel.currentInvoice.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(spacing.medium)
    ) {

        Text(
            text = invoice.value?.business?.name.toStringOrEmpty(),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = invoice.value?.business?.address.toStringOrEmpty(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Divider(modifier = Modifier.padding(top = spacing.medium))

        Text(
            text = stringResource(id = R.string.invoice),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Divider(modifier = Modifier.padding(bottom = spacing.medium))

        Row(
            modifier = Modifier.padding(bottom = spacing.medium)
        ) {
            Column(
                modifier = Modifier.weight(0.6f)
            ) {
                Text(
                    text = stringResource(id = R.string.to),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = invoice.value?.customer?.name.toStringOrEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = invoice.value?.customer?.address.toStringOrEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier.weight(0.4f)
            ) {
                Text(
                    text = stringResource(id = R.string.date),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = invoice.value?.invoice?.createdAt.toDateTime(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }


        Row {
            TableCell(
                text = stringResource(id = R.string.particulars),
                heading = true,
                weight = 0.45f
            )
            TableCell(
                text = stringResource(id = R.string.qty),
                alignment = TextAlign.End,
                heading = true,
                weight = 0.15f
            )
            TableCell(
                text = stringResource(id = R.string.price),
                alignment = TextAlign.End,
                heading = true,
                weight = 0.2f
            )
            TableCell(
                text = stringResource(id = R.string.amount),
                alignment = TextAlign.End,
                heading = true,
                weight = 0.2f
            )
        }

        invoice.value?.items?.forEach { item ->
            Row {
                TableCell(text = item.description, weight = 0.45f)
                TableCell(
                    text = item.quantity.toString(),
                    alignment = TextAlign.End,
                    weight = 0.15f
                )
                TableCell(text = item.price.toString(), alignment = TextAlign.End, weight = 0.2f)
                TableCell(text = item.amount.toString(), alignment = TextAlign.End, weight = 0.2f)
            }
        }

        Row {
            TableCell(
                text = stringResource(id = R.string.total_amount),
                heading = true,
                alignment = TextAlign.End,
                weight = 0.8f
            )
            TableCell(
                text = invoice.value?.totalAmount.toString(),
                alignment = TextAlign.End,
                weight = 0.2f
            )
        }

        Row {
            TableCell(
                text = invoice.value?.tax?.taxLabel.toStringOrEmpty(),
                heading = true,
                alignment = TextAlign.End,
                weight = 0.8f
            )
            TableCell(
                text = invoice.value?.taxAmount.toString(),
                alignment = TextAlign.End,
                weight = 0.2f
            )
        }

        Row {
            TableCell(
                text = stringResource(id = R.string.invoice_amount),
                heading = true,
                alignment = TextAlign.End,
                weight = 0.8f
            )
            TableCell(
                text = invoice.value?.invoiceAmount.toString(),
                alignment = TextAlign.End,
                weight = 0.2f
            )
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun InvoiceDetailPreviewLight() {
    AppTheme {
        InvoiceDetail(FakeViewModelProvider.provideInvoicesViewModel())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun InvoiceDetailPreviewDark() {
    AppTheme {
        InvoiceDetail(FakeViewModelProvider.provideInvoicesViewModel())
    }
}