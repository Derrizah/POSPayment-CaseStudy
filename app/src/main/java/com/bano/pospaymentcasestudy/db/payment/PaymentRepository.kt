package com.bano.pospaymentcasestudy.db.payment

import androidx.lifecycle.LiveData
import javax.inject.Inject

open class PaymentRepository @Inject constructor(private val paymentDAO: PaymentDAO) {
    val payments = paymentDAO.getAllPayments()

    open suspend fun insert(paymentEntity: Payment): Long {
        return paymentDAO.insertPayment(paymentEntity)
    }

    open suspend fun getAllPayments(): LiveData<List<Payment>> {
        return paymentDAO.getAllPayments()
    }
}