package com.bano.pospaymentcasestudy.customerInfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bano.pospaymentcasestudy.PostPayment
import com.bano.pospaymentcasestudy.base.BaseViewModel
import com.bano.pospaymentcasestudy.db.payment.Payment
import com.bano.pospaymentcasestudy.db.payment.PaymentRepository
import kotlinx.coroutines.launch
import java.util.*

class CustomerInfoViewModel(private val paymentRepository: PaymentRepository): BaseViewModel() {
    val payments = paymentRepository.payments

    var paymentComplete: MutableLiveData<Boolean> = MutableLiveData()

    fun proceedPayment(qrString: String, receiptAmount: Int){
        viewModelScope.launch {
//            val paymentActionList = listOf(PaymentAction(amount = receiptAmount.value!!))
//            val paymentInfoList = listOf(PaymentInfo(paymentActionList = paymentActionList))
            val response = paymentService.postPayment(PostPayment(qrData = qrString))
            Log.v("proceedPayment", "Response Code: " + response.code().toString() + " Response: " + response.body().toString())
            if(response.isSuccessful) {
                paymentComplete.value = true

                paymentRepository.insert(
                    Payment(
                    0,
                    receiptAmount,
                    Calendar.getInstance().time.toString(),
                    response.body()!!.sessionID,
                    qrString)
                )
            }
        }
    }
}