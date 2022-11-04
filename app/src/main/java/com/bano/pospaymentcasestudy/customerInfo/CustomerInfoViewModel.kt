package com.bano.pospaymentcasestudy.customerInfo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bano.pospaymentcasestudy.api.request.PostPayment
import com.bano.pospaymentcasestudy.base.BaseViewModel
import com.bano.pospaymentcasestudy.db.payment.Payment
import kotlinx.coroutines.launch
import java.util.*

class CustomerInfoViewModel() : BaseViewModel() {
    //    val payments = paymentRepository.payments
    lateinit var payments: LiveData<List<Payment>>

    var paymentComplete: MutableLiveData<Boolean> = MutableLiveData()

    override fun inject(appContext: Context) {
        super.inject(appContext)
        payments = paymentRepository.payments
    }

    fun proceedPayment(qrString: String, receiptAmount: Int) {
        viewModelScope.launch {
            val response = paymentService.postPayment(PostPayment(qrData = qrString))
            if (response.isSuccessful) {
                paymentComplete.value = true

                paymentRepository.insert(
                    Payment(
                        0,
                        receiptAmount,
                        Date().time.toString(),
                        response.body()!!.sessionID,
                        qrString
                    )
                )
            }
        }
    }
}