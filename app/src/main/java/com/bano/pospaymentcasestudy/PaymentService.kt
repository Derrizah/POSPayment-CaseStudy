package com.bano.pospaymentcasestudy

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PaymentService {
    @POST("get_qr_sale")
    suspend fun getQRForSale(@Body qrForSale: QRForSale): Response<QRResponse>

    @POST("payment")
    suspend fun postPayment(@Body postPayment: PostPayment): Response<PaymentResponse>
}