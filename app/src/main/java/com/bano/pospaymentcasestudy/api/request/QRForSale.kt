package com.bano.pospaymentcasestudy.api.request

import com.google.gson.annotations.SerializedName

data class QRForSale(
    @SerializedName("totalReceiptAmount") val totalReceiptAmount: Int
)