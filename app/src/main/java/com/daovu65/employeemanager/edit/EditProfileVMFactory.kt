package com.daovu65.employeemanager.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daovu65.employeeManager.domain.interacter.CreateEmployee
import com.daovu65.employeeManager.domain.interacter.DeleteEmployee
import com.daovu65.employeeManager.domain.interacter.GetEmployeeById
import com.daovu65.employeeManager.domain.interacter.UpdateEmployee

class EditProfileVMFactory(
    private val updateEmployee: UpdateEmployee,
    private val deleteEmployee: DeleteEmployee,
    private val createEmployee: CreateEmployee,
    private val getEmployeeById: GetEmployeeById
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditProfileViewModel(
            updateEmployee = updateEmployee,
            deleteEmployee = deleteEmployee,
            createEmployee = createEmployee,
            getEmployeeById = getEmployeeById
        ) as T
    }

}