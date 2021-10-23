package com.riyazuddin.noteit.common

import com.riyazuddin.noteit.common.Constants.IGNORE_URLS
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class AuthInterceptor: Interceptor {

    private var token: String = ""

    fun setToken(token: String?) {
        this.token = "Bearer $token"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.url.encodedPath in IGNORE_URLS)
            return chain.proceed(request)
        Timber.i(request.url.encodedPath)
        Timber.i(token)
        val newRequest = request.newBuilder()
            .addHeader("Authorization", token)
            .build()
        return chain.proceed(newRequest)
    }
}