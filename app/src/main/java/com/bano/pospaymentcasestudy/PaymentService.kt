package com.bano.pospaymentcasestudy

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PaymentService {
//    @Headers({
//        "x-ibm-client-id: d56a0277-2ee3-4ae5-97c8-467abeda984d",
//        "x-ibm-client-secret: U1wY2tV5dU2rO7iF7qI7wI4sH8pF0vO8oQ2fE1iS5tU4vW5kO1"
//    })
    @POST("get_qr_sale")
    suspend fun getQRForSale(@Body qrForSale: QRForSale): Response<QRResponse>

    @Headers(
        "x-ibm-client-id: d56a0277-2ee3-4ae5-97c8-467abeda984d",
        "x-ibm-client-secret: U1wY2tV5dU2rO7iF7qI7wI4sH8pF0vO8oQ2fE1iS5tU4vW5kO1"
    )
    @POST("payment")
    suspend fun postPayment(@Body totalReceiptAmount: Int): Response<PaymentResponse>
}