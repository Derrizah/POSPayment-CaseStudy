package com.bano.pospaymentcasestudy.base

import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.bano.pospaymentcasestudy.components.DaggerAppComponent
import com.bano.pospaymentcasestudy.api.PaymentService
import com.bano.pospaymentcasestudy.db.payment.PaymentRepository
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import javax.inject.Inject

abstract class BaseViewModel: ViewModel() {
    @Inject
    lateinit var paymentService: PaymentService

    fun init(){
        DaggerAppComponent.create().inject(this)
    }

    fun getQRBitmap(string: String): Bitmap {
        val size = 512 //pixels
        val bits = QRCodeWriter().encode(string, BarcodeFormat.QR_CODE, size, size)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
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