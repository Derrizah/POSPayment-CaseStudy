package com.bano.pospaymentcasestudy.pos

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bano.pospaymentcasestudy.api.request.QRForSale
import com.bano.pospaymentcasestudy.base.BaseViewModel
import kotlinx.coroutines.launch

class POSViewModel : BaseViewModel() {
    var qrImage: MutableLiveData<Bitmap> = MutableLiveData()
    var receiptAmount: MutableLiveData<Int> = MutableLiveData()
    var qrString: MutableLiveData<String> = MutableLiveData()

    fun getQRCodeForSale(qrForSale: QRForSale) {
        receiptAmount.value = qrForSale.totalReceiptAmount
        viewModelScope.launch {
            val response = paymentService.getQRForSale(qrForSale)
            if (response.isSuccessful) {
                qrString.value = response.body()?.QRdata
                qrImage.postValue(getQRBitmap(qrString.value!!))
            }
        }
    }
}