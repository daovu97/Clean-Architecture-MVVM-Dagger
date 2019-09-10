package com.daovu65.employeemanager.injection.module

import com.daovu65.employeeManager.data.service.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {
    companion object {
        private const val BASE_URL = "http://dummy.restapiexample.com/api/v1/"
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder()
        .readTimeout(10000, TimeUnit.MILLISECONDS)
        .writeTimeout(10000, TimeUnit.MILLISECONDS)
        .connectTimeout(10000, TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(true)
        .protocols(listOf(Protocol.HTTP_1_1))
        .build()

    @Provides
    @Singleton
    fun getService(gson: Gson, okHttpClient: OkHttpClient): ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ApiService::class.java)
}
