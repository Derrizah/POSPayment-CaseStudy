package com.bano.pospaymentcasestudy.api

import com.bano.pospaymentcasestudy.PostPayment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject

interface PaymentService {
    @POST("get_qr_sale")
    suspend fun getQRForSale(@Body qrForSale: QRForSale): Response<QRResponse>

    @POST("payment")
    suspend fun postPayment(@Body postPayment: PostPayment): Response<PaymentResponse>
}