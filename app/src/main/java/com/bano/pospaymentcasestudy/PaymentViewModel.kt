package com.bano.pospaymentcasestudy

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.launch

open class PaymentViewModel(private val paymentRepository: PaymentRepository): ViewModel() {
    lateinit var paymentService: PaymentService
    var qrImage: MutableLiveData<Bitmap> = MutableLiveData()

    fun init() {
    paymentService = RetrofitHelper.getInstance().create(PaymentService::class.java)
    }

    fun getQRCodeForSale(qrForSale: QRForSale) {
        viewModelScope.launch{
            val result = paymentService.getQRForSale(qrForSale)
            Log.v("MainActivity", "response code: " + result.code().toString())
            if(result.isSuccessful) {
                val size = 512 //pixels
                val qrCodeContent = result.body()?.QRdata
                val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size)
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
}