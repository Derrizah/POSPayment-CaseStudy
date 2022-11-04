package com.bano.pospaymentcasestudy.api

import com.google.gson.annotations.SerializedName

data class QRResponse (
    @SerializedName("returnCode") val returnCode: Int,
    @SerializedName("returnDesc") val returnDesc: String,
    @SerializedName("QRdata") val QRdata: String,
)