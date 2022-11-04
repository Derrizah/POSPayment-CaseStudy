package com.bano.pospaymentcasestudy.api

import com.google.gson.annotations.SerializedName

data class QRForSale (
    @SerializedName("totalReceiptAmount") val totalReceiptAmount: Int
)