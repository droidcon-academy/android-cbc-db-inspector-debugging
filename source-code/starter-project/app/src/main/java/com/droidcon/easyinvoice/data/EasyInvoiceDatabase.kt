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

package com.droidcon.easyinvoice.data

import android.content.ContentValues
import android.content.Context
import androidx.room.Database
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.droidcon.easyinvoice.data.daos.BusinessDao
import com.droidcon.easyinvoice.data.daos.CustomerDao
import com.droidcon.easyinvoice.data.daos.InvoiceDao
import com.droidcon.easyinvoice.data.daos.TaxDao
import com.droidcon.easyinvoice.data.entities.*
import com.droidcon.easyinvoice.data.utils.roundDouble
import kotlin.random.Random

@Database(
    entities = [Business::class, Customer::class, Tax::class, Invoice::class, InvoiceItem::class],
    version = 1,
    exportSchema = false
)
abstract class EasyInvoiceDatabase : RoomDatabase() {

    abstract fun getBusinessDao(): BusinessDao
    abstract fun getCustomerDao(): CustomerDao
    abstract fun getTaxDao(): TaxDao
    abstract fun getInvoiceDao(): InvoiceDao

    companion object {
        private const val DB_NAME = "easy_invoice_db"

        @Volatile
        private var instance: EasyInvoiceDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room
            .databaseBuilder(
                context.applicationContext,
                EasyInvoiceDatabase::class.java,
                DB_NAME
            )
            .fallbackToDestructiveMigration()
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.beginTransaction()

                    val insertedCustomers = insertCustomers(db)
                    val insertedBusinesses = insertBusinesses(db)
                    val insertedTaxes = insertTaxes(db)
                    val insertedInvoices = insertInvoices(
                        db,
                        insertedCustomers,
                        insertedBusinesses,
                        insertedTaxes
                    )
                    insertInvoiceItems(db, insertedInvoices)

                    db.setTransactionSuccessful()
                    db.endTransaction()
                }
            })
            .build()

        private fun insertCustomers(db: SupportSQLiteDatabase): Int {
            val customers = arrayListOf<Customer>()
            customers.add(Customer("Alpha", "Albania", "+355", "a@gmail.com"))
            customers.add(Customer("Beta", "Bahrain", "+973", "b@gmail.com"))
            customers.add(Customer("Cupcake", "Canada", "+1", "c@gmail.com"))
            customers.add(Customer("Donut", "Djibouti", "+253", "d@gmail.com"))
            customers.add(Customer("Eclair", "Egypt", "+20", "e@gmail.com"))
            customers.add(Customer("Froyo", "Finland", "+358", "f@gmail.com"))
            customers.add(Customer("Gingerbread", "Germany", "+49", "g@gmail.com"))
            customers.add(Customer("Honeycomb", "Hungary", "+36", "h@gmail.com"))
            customers.add(Customer("Ice Cream Sandwich", "India", "+91", "i@gmail.com"))
            customers.add(Customer("Jelly Bean", "Jordan", "+962", "j@gmail.com"))
            customers.add(Customer("KitKat", "Kenya", "+254", "k@gmail.com"))
            customers.add(Customer("Lollipop", "Lithuania", "+370", "l@gmail.com"))
            customers.add(Customer("Marshmallow", "Madagascar", "+261", "m@gmail.com"))
            customers.add(Customer("Nougat", "New Zealand", "+64", "n@gmail.com"))
            customers.add(Customer("Oreo", "Oman", "+968", "o@gmail.com"))
            customers.add(Customer("Pie", "Palestine", "+970", "p@gmail.com"))

            for (customer in customers) {
                val values = ContentValues()
                values.put("name", customer.name)
                values.put("address", customer.address)
                values.put("phone", customer.phone)
                values.put("email", customer.email)
                db.insert("customer", OnConflictStrategy.ABORT, values)
            }
            return customers.size
        }

        private fun insertBusinesses(db: SupportSQLiteDatabase): Int {
            val businesses = arrayListOf<Business>()
            businesses.add(Business("Android Q", "Qatar", "+974", "q@gmail.com"))
            businesses.add(Business("Android R", "Russia", "+7", "r@gmail.com"))
            businesses.add(Business("Android S", "Saudi Arabia", "+966", "s@gmail.com"))
            businesses.add(Business("Android T", "Thailand", "+66", "t@gmail.com"))
            businesses.add(Business("Android U", "United Kingdom", "+44", "u@gmail.com"))

            for (business in businesses) {
                val values = ContentValues()
                values.put("name", business.name)
                values.put("address", business.address)
                values.put("phone", business.phone)
                values.put("email", business.email)
                db.insert("business", OnConflictStrategy.ABORT, values)
            }
            return businesses.size
        }

        private fun insertTaxes(db: SupportSQLiteDatabase): Int {
            val taxes = arrayListOf<Tax>()
            taxes.add(Tax("Using Compose Tax", 15.5))
            taxes.add(Tax("Using Java Tax", 99.9))
            taxes.add(Tax("Using XML Tax", 30.2))

            for (tax in taxes) {
                val values = ContentValues()
                values.put("description", tax.description)
                values.put("value", tax.value)
                db.insert("tax", OnConflictStrategy.ABORT, values)
            }
            return taxes.size
        }

        private fun insertInvoices(
            db: SupportSQLiteDatabase,
            insertedCustomers: Int,
            insertedBusinesses: Int,
            insertedTaxes: Int
        ): Int {
            val invoices = arrayListOf<Invoice>()
            invoices.add(
                Invoice(
                    businessId = (1..insertedBusinesses).random(),
                    customerId = (1..insertedCustomers).random(),
                    taxId = (1..insertedTaxes).random(),
                    isPaid = true
                )
            )
            invoices.add(
                Invoice(
                    businessId = (1..insertedBusinesses).random(),
                    customerId = (1..insertedCustomers).random(),
                    taxId = (1..insertedTaxes).random(),
                    isPaid = false
                )
            )
            invoices.add(
                Invoice(
                    businessId = (1..insertedBusinesses).random(),
                    customerId = (1..insertedCustomers).random(),
                    taxId = (1..insertedTaxes).random(),
                    isPaid = true
                )
            )

            for (invoice in invoices) {
                val values = ContentValues()
                values.put("business_id", invoice.businessId)
                values.put("customer_id", invoice.customerId)
                values.put("tax_id", invoice.taxId)
                values.put("created_at", invoice.createdAt)
                values.put("is_paid", invoice.isPaid)
                db.insert("invoice", OnConflictStrategy.ABORT, values)
            }
            return invoices.size
        }

        private fun insertInvoiceItems(db: SupportSQLiteDatabase, insertedInvoices: Int) {
            val items = arrayListOf<InvoiceItem>()
            items.add(
                InvoiceItem(
                    "Variables",
                    roundDouble(Random.nextDouble(1.0, 6.0)),
                    roundDouble(Random.nextDouble(10.0, 24.0)),
                    (1..insertedInvoices).random()
                )
            )
            items.add(
                InvoiceItem(
                    "Loops",
                    roundDouble(Random.nextDouble(1.0, 6.0)),
                    roundDouble(Random.nextDouble(10.0, 24.0)),
                    (1..insertedInvoices).random()
                )
            )
            items.add(
                InvoiceItem(
                    "Operators",
                    roundDouble(Random.nextDouble(1.0, 6.0)),
                    roundDouble(Random.nextDouble(10.0, 24.0)),
                    (1..insertedInvoices).random()
                )
            )
            items.add(
                InvoiceItem(
                    "Functions",
                    roundDouble(Random.nextDouble(1.0, 6.0)),
                    roundDouble(Random.nextDouble(10.0, 24.0)),
                    (1..insertedInvoices).random()
                )
            )
            items.add(
                InvoiceItem(
                    "Encapsulation",
                    roundDouble(Random.nextDouble(1.0, 6.0)),
                    roundDouble(Random.nextDouble(10.0, 24.0)),
                    (1..insertedInvoices).random()
                )
            )
            items.add(
                InvoiceItem(
                    "Polymorphism",
                    roundDouble(Random.nextDouble(1.0, 6.0)),
                    roundDouble(Random.nextDouble(10.0, 24.0)),
                    (1..insertedInvoices).random()
                )
            )
            items.add(
                InvoiceItem(
                    "Inheritance",
                    roundDouble(Random.nextDouble(1.0, 6.0)),
                    roundDouble(Random.nextDouble(10.0, 24.0)),
                    (1..insertedInvoices).random()
                )
            )
            items.add(
                InvoiceItem(
                    "Abstraction",
                    roundDouble(Random.nextDouble(1.0, 6.0)),
                    roundDouble(Random.nextDouble(10.0, 24.0)),
                    (1..insertedInvoices).random()
                )
            )
            items.add(
                InvoiceItem(
                    "Exceptions",
                    roundDouble(Random.nextDouble(1.0, 6.0)),
                    roundDouble(Random.nextDouble(10.0, 24.0)),
                    (1..insertedInvoices).random()
                )
            )
            items.add(
                InvoiceItem(
                    "Threads",
                    roundDouble(Random.nextDouble(1.0, 6.0)),
                    roundDouble(Random.nextDouble(10.0, 24.0)),
                    (1..insertedInvoices).random()
                )
            )
            items.add(
                InvoiceItem(
                    "Comments",
                    roundDouble(Random.nextDouble(1.0, 6.0)),
                    roundDouble(Random.nextDouble(10.0, 24.0)),
                    (1..insertedInvoices).random()
                )
            )
            items.add(
                InvoiceItem(
                    "Conditions",
                    roundDouble(Random.nextDouble(1.0, 6.0)),
                    roundDouble(Random.nextDouble(10.0, 24.0)),
                    (1..insertedInvoices).random()
                )
            )

            for (invoice in items) {
                val values = ContentValues()
                values.put("description", invoice.description)
                values.put("quantity", invoice.quantity)
                values.put("price", invoice.price)
                values.put("invoice_id", invoice.invoiceId)
                db.insert("invoice_item", OnConflictStrategy.ABORT, values)
            }
        }



    }
}