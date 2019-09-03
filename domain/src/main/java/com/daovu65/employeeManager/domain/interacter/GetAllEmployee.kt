package com.daovu65.employeeManager.domain.interacter

import com.daovu65.employeeManager.domain.entity.Employee
import com.daovu65.employeeManager.domain.repository.Repository

class GetAllEmployee constructor(private val repository: Repository) {
    suspend fun invoke(result: (List<Employee>?, Throwable?) -> Unit) {
        repository.getAllEmployee { success, error ->
            result(success, error)
        }
    }
}