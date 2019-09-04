package com.daovu65.employeeManager.data

import retrofit2.http.*

interface ApiService {

    @Headers("Accept: application/json")
    @GET("employees")
    suspend fun getAllEmployee(): List<EmployeeEntity>

    @Headers("Accept: application/json")
    @GET("employee/{id}")
    suspend fun getEmployee(@Path("id") id: String): EmployeeEntity

    @DELETE("delete/{id}")
    suspend fun deleteEmployee(@Path("id") id: String): DeleteResponse

    @Headers("Content-Type: application/json")
    @POST("create")
    suspend fun creatEmployee(@Body createRespone: CreateRespone): CreateRespone

    @Headers("Content-Type: application/json")
    @PUT("update/{id}")
    suspend fun updateEmployee(@Path("id") id: String, @Body createRespone: CreateRespone): CreateRespone
}