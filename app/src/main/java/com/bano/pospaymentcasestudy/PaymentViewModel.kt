package com.bano.pospaymentcasestudy

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.launch

open class PaymentViewModel(private val paymentRepository: PaymentRepository): ViewModel() {
    lateinit var paymentService: PaymentService

    var qrImage: MutableLiveData<Bitmap> = MutableLiveData()
    var receiptAmount: MutableLiveData<Int> = MutableLiveData()
    var qrString: MutableLiveData<String> = MutableLiveData()

    fun init() {
    paymentService = RetrofitHelper.getInstance().create(PaymentService::class.java)
    }

    fun getQRCodeForSale(qrForSale: QRForSale) {
        receiptAmount.value = qrForSale.totalReceiptAmount
        viewModelScope.launch{
            val response = paymentService.getQRForSale(qrForSale)
            Log.v("MainActivity", "response code: " + response.code().toString())
            if(response.isSuccessful) {
                val size = 512 //pixels
                qrString.value = response.body()?.QRdata
                val bits = QRCodeWriter().encode(qrString.value, BarcodeFormat.QR_CODE, size, size)
                qrImage.postValue(Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
                    for (x in 0 until size) {
                        for (y in 0 until size) {
                            it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                        }
                    }
                })
            }
        }
    }

    fun proceedPayment(){
        viewModelScope.launch {
            val paymentInfoList = listOf(PaymentInfo(amount = receiptAmount.value!!))
            val response = paymentService.postPayment(PostPayment(paymentInfoList = paymentInfoList, qrData = qrString.value!!))
        }
    }
}
fun <T> MutableLiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}