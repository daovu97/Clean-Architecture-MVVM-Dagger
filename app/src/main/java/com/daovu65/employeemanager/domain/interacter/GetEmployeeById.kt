package com.daovu65.employeemanager.domain.interacter

import com.daovu65.employeemanager.domain.entity.Employee
import com.daovu65.employeemanager.domain.repository.Repository
import retrofit2.http.Path

class GetEmployeeById constructor(private val repository: Repository) {
    suspend fun invoke(@Path("id") id: String, result: (Employee?, Throwable?) -> Unit) {
        repository.getEmployeeById(id) { employee, throwable ->
            result(employee, throwable)
        }
    }
}