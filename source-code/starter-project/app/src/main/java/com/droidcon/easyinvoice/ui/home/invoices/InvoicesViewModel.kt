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

import androidx.lifecycle.viewModelScope
import com.droidcon.easyinvoice.data.entities.*
import com.droidcon.easyinvoice.repositories.BusinessRepository
import com.droidcon.easyinvoice.repositories.CustomerRepository
import com.droidcon.easyinvoice.repositories.InvoiceRepository
import com.droidcon.easyinvoice.repositories.TaxRepository
import com.droidcon.easyinvoice.ui.home.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoicesViewModel @Inject constructor(
    private val invoiceRepository: InvoiceRepository, private val businessRepository: BusinessRepository, private val customerRepository: CustomerRepository, private val taxRepository: TaxRepository
) : BaseViewModel<Invoice>() {

    val businessId = MutableStateFlow<Int?>(null)
    val customerId = MutableStateFlow<Int?>(null)
    val taxId = MutableStateFlow<Int?>(null)
    val invoiceId = MutableStateFlow<Long?>(null)
    val currentInvoice = MutableStateFlow<InvoiceWithItems?>(null)

    private val _invoices = MutableStateFlow<List<InvoiceWithItems>>(listOf())
    val invoices: StateFlow<List<InvoiceWithItems>> = _invoices

    private val _businesses = MutableStateFlow<List<Business>>(listOf())
    val businesses: StateFlow<List<Business>> = _businesses

    private val _customers = MutableStateFlow<List<Customer>>(listOf())
    val customers: StateFlow<List<Customer>> = _customers

    private val _taxes = MutableStateFlow<List<Tax>>(listOf())
    val taxes: StateFlow<List<Tax>> = _taxes

    init {
        viewModelScope.launch { businessRepository.getBusinesses().collect { _businesses.value = it } }
        viewModelScope.launch { customerRepository.getCustomers().collect { _customers.value = it } }
        viewModelScope.launch { taxRepository.getTaxes().collect { _taxes.value = it } }
        viewModelScope.launch { invoiceRepository.getInvoices().collect { _invoices.value = it } }
    }

    override fun validateInputs() {}

    override fun clearInputs() {}

    fun addUpdateInvoice() = viewModelScope.launch {
        val invoice = Invoice(
            businessId = businessId.value!!,
            customerId = customerId.value!!,
            taxId = taxId.value!!
        ).also { it.id = isUpdating.value?.id }
        invoiceId.value = invoiceRepository.addInvoice(invoice)
        resetUpdating()
    }

    fun deleteInvoice(id: Int?) = viewModelScope.launch {
        invoiceRepository.deleteInvoice(id)
    }

    fun setPaidStatus(invoiceId: Int, status: Boolean) = viewModelScope.launch {
        invoiceRepository.setPaidStatus(invoiceId, status)
    }
}