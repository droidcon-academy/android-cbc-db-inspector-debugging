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

import com.droidcon.easyinvoice.data.daos.InvoiceDao
import com.droidcon.easyinvoice.data.entities.Invoice
import com.droidcon.easyinvoice.data.entities.InvoiceItem
import com.droidcon.easyinvoice.data.entities.InvoiceWithItems
import kotlinx.coroutines.flow.Flow

class InvoiceRepositoryImpl(
    private val dao: InvoiceDao
) : InvoiceRepository {

    override suspend fun addInvoice(invoice: Invoice): Long {
        return dao.addUpdateInvoice(invoice)
    }

    override suspend fun addInvoiceItem(invoiceItem: InvoiceItem) {
        dao.addUpdateInvoiceItem(invoiceItem)
    }

    override suspend fun updateInvoice(invoice: Invoice) {
        dao.addUpdateInvoice(invoice)
    }

    override suspend fun updateInvoiceItem(invoiceItem: InvoiceItem) {
        dao.addUpdateInvoiceItem(invoiceItem)
    }

    override suspend fun deleteInvoiceItem(id: Int?) {
        val item = InvoiceItem("", 0.0, 0.0, -1).also { it.id = id }
        dao.deleteInvoiceItem(item)
    }

    override fun getInvoices(): Flow<List<InvoiceWithItems>> {
        return dao.getInvoices()
    }

    override fun getInvoiceItems(invoiceId: Int): Flow<List<InvoiceItem>> {
        return dao.getInvoiceItems(invoiceId)
    }

    override suspend fun setPaidStatus(invoiceId: Int, status: Boolean) {
        dao.setPaidStatus(invoiceId, status)
    }

    override suspend fun deleteInvoice(id: Int?) {
        val invoice = Invoice(businessId = -1, customerId = -1, taxId = -1).also { it.id = id }
        dao.deleteInvoice(invoice)
    }
}