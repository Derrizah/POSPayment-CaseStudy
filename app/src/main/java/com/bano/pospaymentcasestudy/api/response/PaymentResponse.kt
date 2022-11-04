package com.bano.pospaymentcasestudy.api.response

import com.google.gson.annotations.SerializedName

/**
 * Data class used to receive payment response.
 */
data class PaymentResponse(
    @SerializedName("applicationID") val applicationID: String,
    @SerializedName("sessionID") val sessionID: String,
    @SerializedName("posID") val posID: String,
    @SerializedName("returnCode") val returnCode: Int,
    @SerializedName("returnDesc") val returnDesc: String
)