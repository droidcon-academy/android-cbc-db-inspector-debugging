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

package com.droidcon.easyinvoice.repositories

import com.droidcon.easyinvoice.data.entities.Invoice
import com.droidcon.easyinvoice.data.entities.InvoiceItem
import com.droidcon.easyinvoice.data.entities.InvoiceWithItems
import kotlinx.coroutines.flow.Flow

interface InvoiceRepository {
    suspend fun addInvoice(invoice: Invoice): Long
    suspend fun addInvoiceItem(invoiceItem: InvoiceItem)

    suspend fun updateInvoice(invoice: Invoice)
    suspend fun updateInvoiceItem(invoiceItem: InvoiceItem)

    suspend fun deleteInvoice(id: Int?)
    suspend fun deleteInvoiceItem(id: Int?)

    fun getInvoices(): Flow<List<InvoiceWithItems>>
    fun getInvoiceItems(invoiceId: Int): Flow<List<InvoiceItem>>

    suspend fun setPaidStatus(invoiceId: Int, status: Boolean)
}