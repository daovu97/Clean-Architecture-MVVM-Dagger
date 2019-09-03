package com.daovu65.employeemanager.domain.interacter

import com.daovu65.employeemanager.domain.entity.Employee
import com.daovu65.employeemanager.domain.repository.Repository

class CreateEmployee constructor(private val repository: Repository) {
    suspend fun invoke(employee: Employee, result: (Boolean, Throwable?) -> Unit) {
        repository.createEmployee(employee) { b, throwable ->
            result(b, throwable)
        }
    }
}