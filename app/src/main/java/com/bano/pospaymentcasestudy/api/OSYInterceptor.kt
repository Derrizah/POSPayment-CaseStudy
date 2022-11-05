package com.bano.pospaymentcasestudy.api

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor used to add authorization headers to HTTP requests
 */
class OSYInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
            .header("x-ibm-client-id", Connectivity.CLIENT_ID)
            .header("x-ibm-client-secret", Connectivity.CLIENT_SECRET)
        return chain.proceed(builder.build())
    }
}