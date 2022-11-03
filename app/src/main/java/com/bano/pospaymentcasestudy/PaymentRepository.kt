package com.bano.pospaymentcasestudy

import androidx.lifecycle.LiveData

open class PaymentRepository private constructor(private val paymentDAO: PaymentDAO) {
    companion object {
        @Volatile
        private var instance: PaymentRepository? = null
        fun getInstance(paymentDAO: PaymentDAO) : PaymentRepository {
            return instance ?: synchronized(this) {
                instance ?: PaymentRepository(paymentDAO).also { instance = it }
            }
        }
    }
    open fun getAllPayments(): LiveData<List<PaymentEntity>> {
        return paymentDAO.getAllPayments()
    }
}