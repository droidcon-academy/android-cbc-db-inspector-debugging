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

package com.droidcon.easyinvoice.ui.home.customers

import androidx.lifecycle.viewModelScope
import com.droidcon.easyinvoice.data.entities.Customer
import com.droidcon.easyinvoice.repositories.CustomerRepository
import com.droidcon.easyinvoice.ui.home.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersViewModel @Inject constructor(
    private val repository: CustomerRepository
) : BaseViewModel<Customer>() {

    val name = MutableStateFlow("")
    val address = MutableStateFlow("")
    val phone = MutableStateFlow("")
    val email = MutableStateFlow("")

    private val _customers = MutableStateFlow<List<Customer>>(listOf())
    val customers: StateFlow<List<Customer>> = _customers

    init {
        getCustomers()
    }


    override fun validateInputs() {
        _areInputsValid.value =
            name.value.trim().isNotEmpty() && address.value.trim().isNotEmpty() &&
                    phone.value.trim().isNotEmpty() && email.value.trim().isNotEmpty()
    }

    override fun clearInputs() {
        name.value = ""
        address.value = ""
        phone.value = ""
        email.value = ""
    }

    override fun setUpdating(item: Customer) {
        super.setUpdating(item)
        name.value = item.name
        address.value = item.address
        phone.value = item.phone
        email.value = item.email
    }

    fun addUpdateCustomer() = viewModelScope.launch {
        val customer = Customer(name.value, address.value, phone.value, email.value).also { it.id = isUpdating.value?.id }
        repository.addUpdateCustomer(customer)
        resetUpdating()
        clearInputs()
    }

    fun deleteCustomer() = viewModelScope.launch {
        repository.deleteCustomer(isUpdating.value?.id)
        resetUpdating()
    }

    private fun getCustomers() = viewModelScope.launch {
        repository.getCustomers().collect {
            _customers.value = it
        }
    }

}