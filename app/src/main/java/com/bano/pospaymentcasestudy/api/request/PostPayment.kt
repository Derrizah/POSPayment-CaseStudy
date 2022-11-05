package com.bano.pospaymentcasestudy.api.request

import com.google.gson.annotations.SerializedName

/**
 * Data class used to send payment request.
 */
data class PostPayment(
    @SerializedName("returnCode") val returnCode: Int = 1,
    @SerializedName("returnDesc") val returnDesc: String = "success",
    @SerializedName("receiptMsgCustomer") val receiptMsgCustomer: String = android.os.Build.MODEL,
    @SerializedName("receiptMsgMerchant") val receiptMsgMerchant: String = android.os.Build.MODEL,
//    val paymentInfoList: List<PaymentInfo>,
    @SerializedName("QRdata") val qrData: String,
)