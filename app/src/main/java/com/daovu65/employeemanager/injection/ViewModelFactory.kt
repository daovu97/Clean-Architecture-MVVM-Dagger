package com.daovu65.employeemanager.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daovu65.employeeManager.domain.interacter.*
import com.daovu65.employeemanager.Main.MainViewModel
import com.daovu65.employeemanager.edit.EditProfileViewModel
import com.daovu65.employeemanager.profile.ProfileViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val updateEmployee: UpdateEmployee,
    private val deleteEmployee: DeleteEmployee,
    private val createEmployee: CreateEmployee,
    private val getEmployeeById: GetEmployeeById,
    private val getAllEmployee: GetAllEmployee
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> {
                return EditProfileViewModel(
                    createEmployee = createEmployee,
                    updateEmployee = updateEmployee,
                    deleteEmployee = deleteEmployee,
                    getEmployeeById = getEmployeeById
                ) as T
            }

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                return MainViewModel(
                    getAllEmployee = getAllEmployee
                ) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                return ProfileViewModel(
                    getEmployeeById = getEmployeeById
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}