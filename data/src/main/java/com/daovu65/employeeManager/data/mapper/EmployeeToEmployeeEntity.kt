package com.daovu65.employeeManager.data.mapper

import com.daovu65.employeeManager.data.entity.EmployeeEntity
import com.daovu65.employeeManager.domain.entity.Employee
import javax.inject.Inject

class EmployeeToEmployeeEntity @Inject constructor() {
    fun map(employee: Employee): EmployeeEntity {
        return EmployeeEntity(
            employeeName = employee.name,
            employeeAge = employee.age,
            employeeSalary = employee.salary
        )
    }
}