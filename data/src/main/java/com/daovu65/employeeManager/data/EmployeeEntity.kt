package com.daovu65.employeeManager.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmployeeEntity(
    @SerializedName("id")
    @Expose
    val id: String? = null,

    @SerializedName("employee_name")
    @Expose
    val employeeName: String? = null,

    @SerializedName("employee_salary")
    @Expose
    val employeeSalary: String? = null,

    @SerializedName("employee_age")
    @Expose
    val employeeAge: String? = null,

    @SerializedName("profile_image")
    @Expose
    val profileImage: String? = null
)

data class DeleteResponse(
    @SerializedName("success")
    @Expose
    val success: Success? = null
)

data class Success(
    @SerializedName("text")
    @Expose
    val text: String
)

data class CreateRespone(
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("salary")
    @Expose
    val salary: String? = null,
    @SerializedName("age")
    @Expose
    val age: String? = null
)

