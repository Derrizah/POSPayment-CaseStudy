package com.bano.pospaymentcasestudy

data class QRResponse (
    val returnCode: Int,
    val returnDesc: String,
    val QRdata: String,
)