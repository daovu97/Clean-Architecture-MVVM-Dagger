package com.daovu65.employeeManager.domain.interacter

import com.daovu65.employeeManager.domain.repository.Repository
import javax.inject.Inject

class DeleteEmployee @Inject constructor(private val repository: Repository) {
    suspend fun invoke(id: String, result: (String?, Throwable?) -> Unit) {
        repository.deleteEmployee(id) { success, throwable ->
            result(success, throwable)
        }
    }
}