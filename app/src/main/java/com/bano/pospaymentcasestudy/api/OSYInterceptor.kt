package com.bano.pospaymentcasestudy.api

import okhttp3.Interceptor
import okhttp3.Response

class OSYInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        .header("x-ibm-client-id", "d56a0277-2ee3-4ae5-97c8-467abeda984d")
        .header(
            "x-ibm-client-secret",
            "U1wY2tV5dU2rO7iF7qI7wI4sH8pF0vO8oQ2fE1iS5tU4vW5kO1"
        )
        return chain.proceed(builder.build())
    }
}