package com.bano.pospaymentcasestudy.api.request

import com.google.gson.annotations.SerializedName

/**
 * Data class used to send payment request.
 */
data class PaymentAction(
    @SerializedName("paymentType") val paymentType: Int = 3,
    @SerializedName("amount") val amount: Int,
    @SerializedName("currencyID") val currencyID: Int = 949,
    @SerializedName("vatRate") val vatRate: Int = 800
)