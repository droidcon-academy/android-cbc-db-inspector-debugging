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

import com.droidcon.easyinvoice.data.daos.BusinessDao
import com.droidcon.easyinvoice.data.entities.Business
import kotlinx.coroutines.flow.Flow

class BusinessRepositoryImpl(
    private val dao: BusinessDao
) : BusinessRepository {

    override suspend fun addUpdateBusiness(business: Business) {
        dao.addUpdateBusiness(business)
    }

    override fun getBusinesses(): Flow<List<Business>> {
        return dao.getBusinesses()
    }

    override suspend fun deleteMyBusiness(id: Int?) {
        dao.deleteBusiness(
            Business("", "", "", "").also {
                it.id = id
            }
        )
    }
}