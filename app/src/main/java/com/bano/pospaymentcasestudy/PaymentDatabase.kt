package com.bano.pospaymentcasestudy

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PaymentEntity::class], version = 1, exportSchema = false)
abstract class PaymentDatabase : RoomDatabase() {
    abstract fun paymentDAO() : PaymentDAO
    companion object {
        @Volatile
        private var instance : PaymentDatabase? = null
        private const val DB_NAME = "Payments.db"
        fun getDatabase(context: Context): PaymentDatabase {
            return instance ?: synchronized(this) {
                val dbInstance = Room.databaseBuilder(context.applicationContext, PaymentDatabase::class.java, DB_NAME)
                    .build()
                instance = dbInstance
                dbInstance
            }
        }
    }
}