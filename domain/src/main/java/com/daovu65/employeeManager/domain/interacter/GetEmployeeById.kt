package com.daovu65.employeeManager.domain.interacter

import com.daovu65.employeeManager.domain.entity.Employee
import com.daovu65.employeeManager.domain.repository.Repository
import javax.inject.Inject

class GetEmployeeById @Inject constructor(private val repository: Repository) {
    suspend fun invoke(id: String, result: (Employee?, Throwable?) -> Unit) {
        repository.getEmployeeById(id) { employ, throwable ->
            result(employ, throwable)
        }
    }
}