package com.bano.pospaymentcasestudy.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bano.pospaymentcasestudy.customerInfo.CustomerInfoViewModel
import com.bano.pospaymentcasestudy.pos.POSViewModel

/**
 * Creates ViewModels using factory pattern
 */
class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(POSViewModel::class.java)) {
            return POSViewModel().apply {
                this.inject(context)
            } as T
        } else if (modelClass.isAssignableFrom(CustomerInfoViewModel::class.java)) {
            return CustomerInfoViewModel().apply {
                this.inject(context)
            } as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}