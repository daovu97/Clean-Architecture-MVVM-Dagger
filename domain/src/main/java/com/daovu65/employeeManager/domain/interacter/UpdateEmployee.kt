package com.daovu65.employeeManager.domain.interacter

import com.daovu65.employeeManager.domain.entity.Employee
import com.daovu65.employeeManager.domain.repository.Repository

class UpdateEmployee constructor(private val repository: Repository) {
    suspend fun invoke(employee: Employee, result: (Boolean, Throwable?) -> Unit) {
        repository.updateEmployee(employee) { b, throwable ->
            result(b, throwable)
        }
    }
}