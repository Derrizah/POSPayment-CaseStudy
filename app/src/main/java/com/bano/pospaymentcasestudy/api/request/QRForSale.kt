package com.bano.pospaymentcasestudy.api.request

import com.google.gson.annotations.SerializedName

/**
 * Data class used to send QR code request.
 */
data class QRForSale(
    @SerializedName("totalReceiptAmount") val totalReceiptAmount: Int
)