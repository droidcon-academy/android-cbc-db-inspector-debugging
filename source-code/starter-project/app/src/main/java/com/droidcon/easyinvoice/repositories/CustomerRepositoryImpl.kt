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

import com.droidcon.easyinvoice.data.daos.CustomerDao
import com.droidcon.easyinvoice.data.entities.Customer
import kotlinx.coroutines.flow.Flow

class CustomerRepositoryImpl(
    private val dao: CustomerDao
) : CustomerRepository {

    override suspend fun addUpdateCustomer(customer: Customer) {
        dao.addUpdateCustomer(customer)
    }

    override fun getCustomers(): Flow<List<Customer>> {
        return dao.getCustomers()
    }

    override suspend fun deleteCustomer(id: Int?) {
        val customer = Customer("", "", "", "").also {
            it.id = id
        }
        dao.deleteCustomer(customer)
    }
}