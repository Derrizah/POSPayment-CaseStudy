package com.bano.pospaymentcasestudy.db.payment

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Payment::class],
    version = 1,
)
abstract class PaymentDatabase : RoomDatabase() {
    abstract fun getDao(): PaymentDAO
}