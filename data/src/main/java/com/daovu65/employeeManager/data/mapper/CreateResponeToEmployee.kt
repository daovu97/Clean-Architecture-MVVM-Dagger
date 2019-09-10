package com.daovu65.employeeManager.data.mapper

import com.daovu65.employeeManager.data.entity.CreateRespone
import com.daovu65.employeeManager.domain.entity.Employee
import javax.inject.Inject

class CreateResponeToEmployee @Inject constructor() {
    fun map(createRespone: CreateRespone): Employee {
        return Employee(
            name = createRespone.name,
            age = createRespone.age,
            salary = createRespone.salary
        )
    }
}