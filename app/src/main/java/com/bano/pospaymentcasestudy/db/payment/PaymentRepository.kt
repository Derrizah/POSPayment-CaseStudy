package com.bano.pospaymentcasestudy.db.payment

import androidx.lifecycle.LiveData
import javax.inject.Inject

/**
 * Triggers payments database operations on DAO
 */
class PaymentRepository @Inject constructor(private val paymentDAO: PaymentDAO) {
    val payments = paymentDAO.getAllPayments()

    suspend fun insert(paymentEntity: Payment): Long {
        return paymentDAO.insertPayment(paymentEntity)
    }

    suspend fun getAllPayments(): LiveData<List<Payment>> {
        return paymentDAO.getAllPayments()
    }
}