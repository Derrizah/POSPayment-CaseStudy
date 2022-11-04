package com.bano.pospaymentcasestudy.pos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class POSViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(POSViewModel::class.java)) {
            return POSViewModel().apply {
                this.init()
            } as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}