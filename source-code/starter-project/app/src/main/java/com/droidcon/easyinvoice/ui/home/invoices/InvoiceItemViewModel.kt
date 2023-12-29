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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.droidcon.easyinvoice.data.entities.*
import com.droidcon.easyinvoice.repositories.InvoiceRepository
import com.droidcon.easyinvoice.ui.home.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InvoiceItemViewModel @AssistedInject constructor(
    private val repository: InvoiceRepository,
    @Assisted private val invoiceId: Int
) : BaseViewModel<InvoiceItem>() {

    val desc = MutableStateFlow("")
    val qty = MutableStateFlow("")
    val price = MutableStateFlow("")

    private val _invoiceItems = MutableStateFlow<List<InvoiceItem>>(listOf())
    val invoiceItems: StateFlow<List<InvoiceItem>> = _invoiceItems

    init {
        viewModelScope.launch {
            repository.getInvoiceItems(invoiceId).collect {
                _invoiceItems.value = it
            }
        }
    }

    override fun validateInputs() {
        _areInputsValid.value = desc.value.isNotEmpty() && qty.value.toDoubleOrNull() != null && price.value.toDoubleOrNull() != null
    }

    override fun clearInputs() {
        desc.value = ""
        qty.value = ""
        price.value = ""
    }

    override fun setUpdating(item: InvoiceItem) {
        super.setUpdating(item)
        desc.value = item.description
        qty.value = item.quantity.toString()
        price.value = item.price.toString()
    }

    fun addUpdateInvoiceItem() = viewModelScope.launch {
        val item = InvoiceItem(desc.value, qty.value.toDouble(), price.value.toDouble(), invoiceId).also {
            it.id = isUpdating.value?.id
        }
        repository.addInvoiceItem(item)
        clearInputs()
        validateInputs()
        resetUpdating()
    }

    fun deleteInvoiceItem(id: Int?) = viewModelScope.launch {
        repository.deleteInvoiceItem(id)
    }

    @AssistedFactory
    interface InvoiceItemViewModelFactory {
        fun create(invoiceId: Int): InvoiceItemViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideInvoiceItemViewModelFactory(
            assistedFactory: InvoiceItemViewModelFactory,
            invoiceId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(invoiceId) as T
            }
        }
    }
}