package com.daovu65.employeeManager.data.mapper

import com.daovu65.employeeManager.data.entity.CreateRespone
import com.daovu65.employeeManager.domain.entity.Employee
import javax.inject.Inject

class EmployeeToCreateResponse @Inject constructor(){
    fun map(employee: Employee): CreateRespone {
        return CreateRespone(
            name = employee.name,
            salary = employee.salary,
            age = employee.age
        )
    }
}