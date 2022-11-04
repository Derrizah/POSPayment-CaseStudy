package com.bano.pospaymentcasestudy.api

import com.bano.pospaymentcasestudy.api.request.PostPayment
import com.bano.pospaymentcasestudy.api.request.QRForSale
import com.bano.pospaymentcasestudy.api.response.PaymentResponse
import com.bano.pospaymentcasestudy.api.response.QRForSaleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PaymentService {
    @POST("get_qr_sale")
    suspend fun getQRForSale(@Body qrForSale: QRForSale): Response<QRForSaleResponse>

    @POST("payment")
    suspend fun postPayment(@Body postPayment: PostPayment): Response<PaymentResponse>
}