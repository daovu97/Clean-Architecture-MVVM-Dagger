package com.daovu65.employeemanager.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daovu65.employeemanager.domain.interacter.GetEmployeeById

class ProfileVMFactory(
    private val getEmployeeById: GetEmployeeById
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(
            getEmployeeById = getEmployeeById
        ) as T
    }

}