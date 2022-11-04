package com.bano.pospaymentcasestudy.api

import com.google.gson.annotations.SerializedName

data class PostPayment (
    @SerializedName("returnCode") val returnCode: Int = 1,
    @SerializedName("returnDesc") val returnDesc: String = "success",
    @SerializedName("receiptMsgCustomer") val receiptMsgCustomer: String = "beko Campaign/n2018",
    @SerializedName("receiptMsgMerchant") val receiptMsgMerchant: String = "beko Campaign Merchant/n2018",
//    val paymentInfoList: List<PaymentInfo>,
    @SerializedName("QRdata") val qrData: String,
)

data class PaymentInfo (
    @SerializedName("paymentProcessorID") val paymentProcessorID: Int = 67,
    @SerializedName("paymentActionList") val paymentActionList: List<PaymentAction>
)

data class PaymentAction(
    @SerializedName("paymentType") val paymentType: Int = 3,
    @SerializedName("amount") val amount: Int,
    @SerializedName("currencyID") val currencyID: Int = 949,
    @SerializedName("vatRate") val vatRate: Int = 800
)