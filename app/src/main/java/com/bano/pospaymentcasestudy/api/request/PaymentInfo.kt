package com.bano.pospaymentcasestudy.api.request

import com.google.gson.annotations.SerializedName

/**
 * Data class used to send payment request.
 */
data class PaymentInfo(
    @SerializedName("paymentProcessorID") val paymentProcessorID: Int = 67,
    @SerializedName("paymentActionList") val paymentActionList: List<PaymentAction>
)