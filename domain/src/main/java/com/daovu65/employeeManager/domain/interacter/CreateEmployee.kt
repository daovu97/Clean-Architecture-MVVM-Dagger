package com.daovu65.employeeManager.domain.interacter

import com.daovu65.employeeManager.domain.entity.Employee
import com.daovu65.employeeManager.domain.repository.Repository
import javax.inject.Inject

class CreateEmployee @Inject constructor(private val repository: Repository) {
    suspend fun invoke(employee: Employee, result: (Employee?, Throwable?) -> Unit) {
        repository.createEmployee(employee) { employ, throwable ->
            result(employ, throwable)
        }
    }
}