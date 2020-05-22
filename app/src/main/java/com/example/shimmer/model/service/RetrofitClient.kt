package com.example.shimmer.model.service

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class RetrofitClient {

    private val Base_Url = "https://5e510330f2c0d300147c034c.mockapi.io/"
    private lateinit var retrofit: Retrofit
    private val request_Time_Out = 6
    var mInstance: RetrofitClient? = null

    init {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(request_Time_Out.toLong(), TimeUnit.MINUTES)
            .readTimeout(request_Time_Out.toLong(), TimeUnit.MINUTES)
            .writeTimeout(request_Time_Out.toLong(), TimeUnit.MINUTES)
            .addInterceptor(
                Interceptor { chain: Interceptor.Chain ->
                    val original: Request = chain.request()
                    val requestBuilder =
                        original.newBuilder().method(original.method(), original.body())
                    val request: Request = requestBuilder.build()
                    return@Interceptor chain.proceed(request)
                }).build()
        retrofit = Retrofit.Builder()
            .baseUrl(Base_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Synchronized
    fun getInstance(): RetrofitClient {
        if (mInstance == null) {
            mInstance = RetrofitClient()
        }
        return mInstance as RetrofitClient
    }

    fun apiService(): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}