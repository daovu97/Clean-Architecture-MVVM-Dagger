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
    override suspend fun createEmployee(
        employee: Employee,
        result: (Employee?, Throwable?) -> Unit
    ): Job = withContext(Dispatchers.IO) {
        launch {
            try {
                val value = apiService.creatEmployee(
                    employee.name!!.trim(),
                    employee.salary!!.trim(),
                    employee.age!!.trim()
                )
                result(Convert.createResponeToEmployee(value), null)
            } catch (e: Throwable) {
                result(null, e)
            }
        }
    }

    override suspend fun getEmployeeById(id: String, result: (Employee?, Throwable?) -> Unit): Job =
        withContext(Dispatchers.IO) {
            launch {
                try {
                    result(
                        Convert.employeeEntityToEmployee(
                            apiService.getEmployee(id)
                        ), null
                    )
                } catch (e: Throwable) {
                    result(null, e)
                }
            }
        }

    override suspend fun deleteEmployee(
        id: String,
        result: (String?, Throwable?) -> Unit
    ): Job = withContext(Dispatchers.IO) {
        launch {
            try {
                val value = apiService.deleteEmployee(id)
                value.success?.let {
                    result(it.text, null)
                }

            } catch (e: Throwable) {
                result(null, e)
            }
        }
    }

    override suspend fun updateEmployee(
        employee: Employee,
        result: (Employee?, Throwable?) -> Unit
    ): Job = withContext(Dispatchers.IO) {
        launch {
            try {
                val value = apiService.updateEmployee(
                    Convert.employeeToEmployeeEntity(employee).id!!,
                    employee.name!!,
                    employee.salary!!,
                    employee.age!!
                )
                result(Convert.createResponeToEmployee(value), null)
            } catch (e: Throwable) {
                result(null, e)
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