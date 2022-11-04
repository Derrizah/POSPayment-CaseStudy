package com.bano.pospaymentcasestudy.pos

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Create POSViewModel using factory pattern
 */
class POSViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(POSViewModel::class.java)) {
            return POSViewModel().apply {
                this.inject(context)
            } as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}