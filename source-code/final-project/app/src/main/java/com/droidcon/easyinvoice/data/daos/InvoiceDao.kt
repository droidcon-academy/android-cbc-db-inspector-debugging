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

package com.droidcon.easyinvoice.data.daos

import androidx.room.*
import com.droidcon.easyinvoice.data.entities.Invoice
import com.droidcon.easyinvoice.data.entities.InvoiceItem
import com.droidcon.easyinvoice.data.entities.InvoiceWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUpdateInvoice(invoice: Invoice): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUpdateInvoiceItem(invoiceItem: InvoiceItem)

    @Transaction
    @Query("SELECT * FROM invoice")
    fun getInvoices(): Flow<List<InvoiceWithItems>>

    @Query("SELECT * FROM invoice_item WHERE invoice_id=:invoiceId")
    fun getInvoiceItems(invoiceId: Int): Flow<List<InvoiceItem>>

    @Delete
    suspend fun deleteInvoice(invoice: Invoice)

    @Delete
    suspend fun deleteInvoiceItem(invoiceItem: InvoiceItem)

    @Query("UPDATE invoice SET is_paid = :status WHERE id=:invoiceId")
    suspend fun setPaidStatus(invoiceId: Int, status: Boolean)
}