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

package com.droidcon.easyinvoice.ui.home.taxes

import androidx.lifecycle.viewModelScope
import com.droidcon.easyinvoice.data.entities.Tax
import com.droidcon.easyinvoice.repositories.TaxRepository
import com.droidcon.easyinvoice.ui.home.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaxesViewModel @Inject constructor(
    private val repository: TaxRepository
) : BaseViewModel<Tax>() {

    val desc = MutableStateFlow("")
    val value = MutableStateFlow("")

    private val _taxes = MutableStateFlow<List<Tax>>(listOf())
    val taxes: StateFlow<List<Tax>> = _taxes

    init {
        getTaxes()
    }

    override fun validateInputs() {
        val validate = desc.value.trim().isNotEmpty() && value.value.trim().isNotEmpty()
        _areInputsValid.value = validate
    }

    override fun clearInputs() {
        desc.value = ""
        value.value = ""
    }

    fun addUpdateTax() = viewModelScope.launch {
        val tax = Tax(desc.value, value.value.toDouble()).also { it.id = isUpdating.value?.id }
        repository.addUpdateTax(tax)
        resetUpdating()
        clearInputs()
    }

    override fun setUpdating(item: Tax) {
        super.setUpdating(item)
        desc.value = item.description
        value.value = item.value.toString()
    }

    fun deleteTax() = viewModelScope.launch {
        repository.deleteTax(isUpdating.value?.id)
        resetUpdating()
    }

    private fun getTaxes() = viewModelScope.launch {
        repository.getTaxes().collect {
            _taxes.value = it
        }
    }
}