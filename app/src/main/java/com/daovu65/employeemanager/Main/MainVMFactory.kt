package com.daovu65.employeemanager.Main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daovu65.employeeManager.domain.interacter.GetAllEmployee

class MainVMFactory(
    private val getAllEmployee: GetAllEmployee
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(getAllEmployee = getAllEmployee) as T
    }

}