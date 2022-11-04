package com.bano.pospaymentcasestudy.db.payment

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PaymentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(paymentEntity: Payment) : Long

    @Query("SELECT * FROM payments_database")
    fun getAllPayments(): LiveData<List<Payment>>
}