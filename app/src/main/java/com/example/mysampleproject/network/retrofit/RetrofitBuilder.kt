package com.example.mysampleproject.network.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    val BASE_URL = "https://jsonplaceholder.typicode.com"

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient())
            .build()
    }

    private fun getHttpClient(): OkHttpClient? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient()
            .newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(AddHeaderInterceptor())
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(interceptor)
            .build()
    }

    class AddHeaderInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
            builder.addHeader("X-Requested-With", "XMLHttpRequest")
            return chain.proceed(builder.build())
        }
    }

}