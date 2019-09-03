package com.daovu65.employeeManager.domain.repository

import com.daovu65.employeeManager.domain.entity.Employee
import kotlinx.coroutines.Job

interface Repository {
    suspend fun getAllEmployee(result: (List<Employee>?, Throwable?) -> Unit): Job

    suspend fun getEmployeeById(id: String, result: (Employee?, Throwable?) -> Unit): Job

    suspend fun createEmployee(employee: Employee, result: (Boolean, Throwable?) -> Unit): Job

    suspend fun deleteEmployee(id: String, result: (Boolean, Throwable?) -> Unit): Job

    suspend fun updateEmployee(employee: Employee, result: (Boolean, Throwable?) -> Unit): Job

}


