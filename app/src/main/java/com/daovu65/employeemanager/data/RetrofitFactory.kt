package com.daovu65.employeemanager.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitFactory {
    companion object {

        @Volatile
        private var INSTANCE: ApiService? = null

        private const val BASE_URL = "http://dummy.restapiexample.com/api/v1/"
    }

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
        .readTimeout(10000, TimeUnit.MILLISECONDS)
        .writeTimeout(10000, TimeUnit.MILLISECONDS)
        .connectTimeout(10000, TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(true)
        .protocols(listOf(Protocol.HTTP_1_1))
        .build()

    fun getService(): ApiService {
        return INSTANCE ?: synchronized(this) {
            val instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
            INSTANCE = instance
            instance
        }
    }


}
