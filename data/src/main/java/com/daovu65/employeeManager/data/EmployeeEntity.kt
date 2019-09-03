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