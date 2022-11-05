package com.bano.pospaymentcasestudy.pos

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bano.pospaymentcasestudy.api.request.QRForSale
import com.bano.pospaymentcasestudy.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * ViewModel used by POSFragment. Handles payments and gives
 * QR bitmap
 */
class POSViewModel : BaseViewModel() {
    var qrImage: MutableLiveData<Bitmap> = MutableLiveData()
    var receiptAmount: MutableLiveData<Int> = MutableLiveData()
    var qrString: MutableLiveData<String> = MutableLiveData()

    /**
     * Request a QR code based on the amount
     */
    fun getQRCodeForSale(amount: Float) {
        val realAmount = (amount * 100).toInt()
        receiptAmount.value = realAmount
        viewModelScope.launch {
            val response = osyService.getQRForSale(QRForSale(realAmount))
            if (response.isSuccessful) {
                qrString.value = response.body()?.QRdata
                qrImage.postValue(getQRBitmap(qrString.value!!))
            }
        }
    }
}