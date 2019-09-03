package com.daovu65.employeeManager.data

import com.daovu65.employeeManager.domain.entity.Employee
import com.daovu65.employeeManager.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoryImpl(
    private val apiService: ApiService
) : Repository {
    override suspend fun getEmployeeById(id: String, result: (Employee?, Throwable?) -> Unit): Job =
        withContext(Dispatchers.IO) {
            launch {
                try {
                    result(
                        Convert.employeeEntityToEmployee(
                            apiService.getEmployee(id)
                        ), null)
                } catch (e: Throwable) {
                    result(null, e)
                }
            }
        }

    override suspend fun createEmployee(
        employee: Employee,
        result: (Boolean, Throwable?) -> Unit
    ): Job = withContext(Dispatchers.IO) {
        launch {
            try {
                apiService.creatEmployee(
                    Convert.employeeToEmployeeEntity(
                        employee
                    )
                )
                result(true, null)
            } catch (e: Throwable) {
                result(false, e)
            }
        }
    }

    override suspend fun deleteEmployee(
        id: String,
        result: (Boolean, Throwable?) -> Unit
    ): Job = withContext(Dispatchers.IO) {
        launch {
            try {
                apiService.deleteEmployee(id)
                result(true, null)
            } catch (e: Throwable) {
                result(false, e)
            }
        }
    }

    override suspend fun updateEmployee(
        employee: Employee,
        result: (Boolean, Throwable?) -> Unit
    ): Job = withContext(Dispatchers.IO) {
        launch {
            try {
                apiService.updateEmployee(
                    Convert.employeeToEmployeeEntity(
                        employee
                    ).id!!)
                result(true, null)
            } catch (e: Throwable) {
                result(false, e)
            }
        }
    }

    override suspend fun getAllEmployee(result: (List<Employee>?, Throwable?) -> Unit): Job =
        withContext(Dispatchers.IO) {
            launch {
                try {
                    result(apiService.getAllEmployee().map {
                        Convert.employeeEntityToEmployee(it)
                    }, null)
                } catch (e: Throwable) {
                    result(null, e)
                }
            }
        }


}