package com.bano.pospaymentcasestudy.api

data class PaymentResponse (
    val applicationID: String,
    val sessionID: String,
    val posID: String,
    val returnCode: Int,
    val returnDesc: String
)