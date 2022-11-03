package com.bano.pospaymentcasestudy

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface PaymentDAO {
    @Query("SELECT * FROM Payments")
    fun getAllPayments(): LiveData<List<PaymentEntity>>
}