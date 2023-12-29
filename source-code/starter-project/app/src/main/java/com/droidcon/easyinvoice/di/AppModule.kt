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

package com.droidcon.easyinvoice.di

import android.content.Context
import com.droidcon.easyinvoice.data.EasyInvoiceDatabase
import com.droidcon.easyinvoice.repositories.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideEasyInvoiceDatabase(@ApplicationContext context: Context): EasyInvoiceDatabase = EasyInvoiceDatabase.invoke(context)

    @Provides
    fun provideBusinessRepository(db: EasyInvoiceDatabase): BusinessRepository = BusinessRepositoryImpl(db.getBusinessDao())

    @Provides
    fun provideCustomerRepository(db: EasyInvoiceDatabase): CustomerRepository = CustomerRepositoryImpl(db.getCustomerDao())

    @Provides
    fun provideTaxRepository(db: EasyInvoiceDatabase): TaxRepository = TaxRepositoryImpl(db.getTaxDao())

    @Provides
    fun provideInvoiceRepository(db: EasyInvoiceDatabase): InvoiceRepository = InvoiceRepositoryImpl(db.getInvoiceDao())
}