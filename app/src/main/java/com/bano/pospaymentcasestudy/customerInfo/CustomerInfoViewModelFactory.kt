package com.bano.pospaymentcasestudy.customerInfo

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CustomerInfoViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerInfoViewModel::class.java)) {
//            val repository = PaymentRepository.getInstance(PaymentDatabase.getInstance(context).paymentDAO)
            return CustomerInfoViewModel().apply {
                this.inject(context)
            } as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}