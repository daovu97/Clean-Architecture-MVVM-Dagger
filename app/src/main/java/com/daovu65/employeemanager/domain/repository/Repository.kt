package com.daovu65.employeemanager.domain.repository

import com.daovu65.employeemanager.domain.entity.Employee
import kotlinx.coroutines.Job
import retrofit2.http.Path

interface Repository {
    suspend fun getAllEmployee(result: (List<Employee>?, Throwable?) -> Unit): Job

    suspend fun getEmployeeById(@Path("id") id: String, result: (Employee?, Throwable?) -> Unit): Job

    suspend fun createEmployee(employee: Employee, result: (Boolean, Throwable?) -> Unit): Job

    suspend fun deleteEmployee(@Path("id") id: String, result: (Boolean, Throwable?) -> Unit): Job

    suspend fun updateEmployee(employee: Employee, result: (Boolean, Throwable?) -> Unit): Job

}


