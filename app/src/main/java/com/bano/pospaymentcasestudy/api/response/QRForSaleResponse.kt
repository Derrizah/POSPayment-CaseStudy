package com.bano.pospaymentcasestudy.api.response

import com.google.gson.annotations.SerializedName

data class QRForSaleResponse(
    @SerializedName("returnCode") val returnCode: Int,
    @SerializedName("returnDesc") val returnDesc: String,
    @SerializedName("QRdata") val QRdata: String,
)