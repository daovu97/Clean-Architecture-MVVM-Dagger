package com.daovu65.employeemanager.data

import com.daovu65.employeemanager.domain.entity.Employee

object Convert {
    fun employeeEntityToEmployee(employeeEntity: EmployeeEntity): Employee {
        return Employee(
            id = employeeEntity.id.toString(),
            name = employeeEntity.employeeName.toString(),
            salary = employeeEntity.employeeSalary.toString(),
            age = employeeEntity.employeeAge.toString(),
            profileImage = employeeEntity.profileImage.toString()
        )
    }

    fun employeeToEmployeeEntity(employee: Employee): EmployeeEntity {
        return EmployeeEntity(
            id = employee.id,
            employeeName = employee.name,
            employeeAge = employee.age,
            employeeSalary = employee.salary
        )
    }
}