package com.bano.pospaymentcasestudy.api

data class QRResponse (
    val returnCode: Int,
    val returnDesc: String,
    val QRdata: String,
)