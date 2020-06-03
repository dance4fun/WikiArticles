package com.tiredbones.wikiarticles.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ApiParametersInterceptor() : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val url = request.url().newBuilder()
        .addQueryParameter(FORMAT_PARAM, FORMAT_JSON).build()
    return chain.proceed(request.newBuilder().url(url).build())
  }

  companion object {
    private const val FORMAT_PARAM = "format"
    private const val FORMAT_JSON = "json"
  }
}
