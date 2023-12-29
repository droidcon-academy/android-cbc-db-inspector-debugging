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

package com.droidcon.easyinvoice.ui.faker

import com.droidcon.easyinvoice.data.entities.*
import com.droidcon.easyinvoice.repositories.*
import com.droidcon.easyinvoice.ui.home.customers.CustomersViewModel
import com.droidcon.easyinvoice.ui.home.invoices.InvoicesViewModel
import com.droidcon.easyinvoice.ui.home.mybusinesses.MyBusinessViewModel
import com.droidcon.easyinvoice.ui.home.taxes.TaxesViewModel
import kotlinx.coroutines.flow.Flow

/*
* Currently there is a problem with *Jetpack Compose Preview* & *Hilt*
* Jetpack compose is not able to inject using hiltViewModel() to generate Compose Previews
* In future when both these libraries will be compatible, we can remove this object
* But for now, to see preview, we can use this FakeViewModelProvider
* */
object FakeViewModelProvider {

    fun provideMyBusinessViewModel(): MyBusinessViewModel = MyBusinessViewModel(businessRepo)

    fun provideCustomerViewModel(): CustomersViewModel = CustomersViewModel(customerRepo)

    fun provideTaxesViewModel(): TaxesViewModel = TaxesViewModel(taxRepo)

    fun provideInvoicesViewModel(): InvoicesViewModel = InvoicesViewModel(invoiceRepo, businessRepo, customerRepo, taxRepo)

    private val businessRepo = object : BusinessRepository {
        override suspend fun addUpdateBusiness(business: Business) {
            TODO("Not yet implemented")
        }

        override fun getBusinesses(): Flow<List<Business>> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteMyBusiness(id: Int?) {
            TODO("Not yet implemented")
        }
    }

    private val customerRepo = object : CustomerRepository {
        override suspend fun addUpdateCustomer(customer: Customer) {
            TODO("Not yet implemented")
        }

        override fun getCustomers(): Flow<List<Customer>> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteCustomer(id: Int?) {
            TODO("Not yet implemented")
        }
    }

    private val taxRepo = object : TaxRepository {
        override suspend fun addUpdateTax(tax: Tax) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteTax(id: Int?) {
            TODO("Not yet implemented")
        }

        override fun getTaxes(): Flow<List<Tax>> {
            TODO("Not yet implemented")
        }

    }

    private val invoiceRepo = object : InvoiceRepository {
        override suspend fun addInvoice(invoice: Invoice): Long {
            TODO("Not yet implemented")
        }

        override suspend fun addInvoiceItem(invoiceItem: InvoiceItem) {
            TODO("Not yet implemented")
        }

        override suspend fun updateInvoice(invoice: Invoice) {
            TODO("Not yet implemented")
        }

        override suspend fun updateInvoiceItem(invoiceItem: InvoiceItem) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteInvoice(id: Int?) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteInvoiceItem(id: Int?) {
            TODO("Not yet implemented")
        }

        override fun getInvoices(): Flow<List<InvoiceWithItems>> {
            TODO("Not yet implemented")
        }

        override fun getInvoiceItems(invoiceId: Int): Flow<List<InvoiceItem>> {
            TODO("Not yet implemented")
        }

        override suspend fun setPaidStatus(invoiceId: Int, status: Boolean) {
            TODO("Not yet implemented")
        }

    }
}