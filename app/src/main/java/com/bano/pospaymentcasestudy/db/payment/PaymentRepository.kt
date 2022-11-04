package com.bano.pospaymentcasestudy.db.payment

import androidx.lifecycle.LiveData

open class PaymentRepository(private val paymentDAO: PaymentDAO) {
    val payments = paymentDAO.getAllPayments()

    companion object {
        @Volatile
        private var instance: PaymentRepository? = null
        fun getInstance(paymentDAO: PaymentDAO) : PaymentRepository {
            return instance ?: synchronized(this) {
                instance ?: PaymentRepository(paymentDAO).also { instance = it }
            }
        }
    }

     open suspend fun insert(paymentEntity: Payment) : Long {
         return paymentDAO.insertPayment(paymentEntity)
     }

    open suspend fun getAllPayments(): LiveData<List<Payment>> {
        return paymentDAO.getAllPayments()
    }
}