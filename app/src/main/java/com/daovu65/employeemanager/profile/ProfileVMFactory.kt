package com.daovu65.employeemanager.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daovu65.employeeManager.domain.interacter.GetEmployeeById

class ProfileVMFactory(
    private val getEmployeeById: GetEmployeeById
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(
            getEmployeeById = getEmployeeById
        ) as T
    }

}