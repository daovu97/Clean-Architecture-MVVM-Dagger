package com.daovu65.employeeManager.data.mapper

import com.daovu65.employeeManager.data.entity.EmployeeEntity
import com.daovu65.employeeManager.domain.entity.Employee
import javax.inject.Inject

class EmployeeEntityToEmployee @Inject constructor() {
    fun map(employeeEntity: EmployeeEntity): Employee {
        return Employee(
            id = employeeEntity.id.toString(),
            name = employeeEntity.employeeName.toString(),
            salary = employeeEntity.employeeSalary.toString(),
            age = employeeEntity.employeeAge.toString(),
            profileImage = employeeEntity.profileImage.toString()
        )
    }
}