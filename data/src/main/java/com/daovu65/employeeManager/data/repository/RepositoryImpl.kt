package com.daovu65.employeeManager.data.repository

import com.daovu65.employeeManager.data.mapper.CreateResponeToEmployee
import com.daovu65.employeeManager.data.mapper.EmployeeEntityToEmployee
import com.daovu65.employeeManager.data.mapper.EmployeeToCreateResponse
import com.daovu65.employeeManager.data.mapper.EmployeeToEmployeeEntity
import com.daovu65.employeeManager.data.service.ApiService
import com.daovu65.employeeManager.domain.entity.Employee
import com.daovu65.employeeManager.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val createResponeToEmployee: CreateResponeToEmployee,
    private val employeeToCreateResponse: EmployeeToCreateResponse,
    private val employeeToEmployeeEntity: EmployeeToEmployeeEntity,
    private val employeeEntityToEmployee: EmployeeEntityToEmployee
) : Repository {
    override suspend fun createEmployee(
        employee: Employee,
        result: (Employee?, Throwable?) -> Unit
    ): Job = withContext(Dispatchers.IO) {
        launch {
            try {
                val value = apiService.creatEmployee(
                    employeeToCreateResponse.map(
                        employee
                    )
                )
                result(createResponeToEmployee.map(value), null)
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
                        employeeEntityToEmployee.map(
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
                employee.id?.let {
                    val value =
                        apiService.updateEmployee(
                            it,
                            employeeToCreateResponse.map(employee)
                        )
                    result(createResponeToEmployee.map(value), null)
                }

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
                        employeeEntityToEmployee.map(it)
                    }, null)
                } catch (e: Throwable) {
                    result(null, e)
                }
            }
        }


}