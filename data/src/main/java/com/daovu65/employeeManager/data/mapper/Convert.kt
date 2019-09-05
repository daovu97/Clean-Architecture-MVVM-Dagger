package com.daovu65.employeeManager.data.mapper

import com.daovu65.employeeManager.data.entity.CreateRespone
import com.daovu65.employeeManager.data.entity.EmployeeEntity
import com.daovu65.employeeManager.domain.entity.Employee


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
            employeeName = employee.name,
            employeeAge = employee.age,
            employeeSalary = employee.salary
        )
    }

    fun createResponeToEmployee(createRespone: CreateRespone): Employee {
        return Employee(
            name = createRespone.name,
            age = createRespone.age,
            salary = createRespone.salary
        )
    }


    fun employeeToCreateResponse(employee: Employee): CreateRespone {
        return CreateRespone(
            name = employee.name,
            salary = employee.salary,
            age = employee.age
        )
    }
}