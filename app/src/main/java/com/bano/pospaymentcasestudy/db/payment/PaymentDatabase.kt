package com.bano.pospaymentcasestudy.db.payment

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Payment::class],
    version = 1,
)
abstract class PaymentDatabase : RoomDatabase() {
    abstract val paymentDAO : PaymentDAO

    companion object {
        @Volatile
        private var INSTANCE : PaymentDatabase? = null
        private const val DB_NAME = "payments_database"
        fun getInstance(context: Context): PaymentDatabase {
            return INSTANCE ?: synchronized(this) {
                val dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    PaymentDatabase::class.java, DB_NAME)
                    .build()
                INSTANCE = dbInstance
                dbInstance
            }
        }
    }
}