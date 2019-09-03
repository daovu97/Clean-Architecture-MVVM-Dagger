package com.daovu65.employeemanager.domain.interacter

import com.daovu65.employeemanager.domain.repository.Repository

class DeleteEmployee constructor(private val repository: Repository) {
    suspend fun invoke(id: String, result: (Boolean, Throwable?) -> Unit) {
        repository.deleteEmployee(id) { b, throwable ->
            result(b, throwable)
        }
    }
}