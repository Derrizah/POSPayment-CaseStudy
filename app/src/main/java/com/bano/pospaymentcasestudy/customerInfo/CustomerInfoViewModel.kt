package com.bano.pospaymentcasestudy.customerInfo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bano.pospaymentcasestudy.api.request.PostPayment
import com.bano.pospaymentcasestudy.api.response.PaymentResponse
import com.bano.pospaymentcasestudy.base.BaseViewModel
import com.bano.pospaymentcasestudy.db.payment.Payment
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * ViewModel used by CustomerInfoFragment. Handles payments and gives
 * access to payment database
 */
class CustomerInfoViewModel : BaseViewModel() {
    lateinit var payments: LiveData<List<Payment>>

    var paymentComplete: MutableLiveData<Boolean> = MutableLiveData()

    var lastInsertedPayment: MutableLiveData<Payment> = MutableLiveData()
    var lastResponse: MutableLiveData<PaymentResponse> = MutableLiveData()

    override fun inject(appContext: Context) {
        super.inject(appContext)
        payments = paymentRepository.payments
    }

    /**
     * Post payment to backend service and, if successful, insert into
     * payments table
     */
    fun proceedPayment(qrString: String, receiptAmount: Float) {
        viewModelScope.launch {
            val response = osyService.postPayment(PostPayment(qrData = qrString))
            lastResponse.postValue(response.body())
            if (response.isSuccessful) {
                paymentComplete.value = true
                addIntoRepo((receiptAmount * 100).toInt(), response.body()!!.sessionID, qrString)
            }
        }
    }

    /**
     * Creates Payment based on provided values and triggers insertion
     */
    fun addIntoRepo(amount: Int, session: String, qr: String) {
        val payment = Payment(
            0,
            amount,
            SimpleDateFormat("MM-dd-yyyy hh:mm:ss").format(Calendar.getInstance().time),
            session,
            qr
        )
        lastInsertedPayment.value = payment
        viewModelScope.launch {
            paymentRepository.insert(payment)
        }
    }
}