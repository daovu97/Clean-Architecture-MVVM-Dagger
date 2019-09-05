package com.daovu65.employeemanager

import com.daovu65.employeeManager.data.repository.RepositoryImpl
import com.daovu65.employeeManager.data.service.RetrofitFactory
import com.daovu65.employeeManager.domain.interacter.*
import com.daovu65.employeemanager.Main.MainActivity
import com.daovu65.employeemanager.Main.MainVMFactory
import com.daovu65.employeemanager.edit.EditProfileActivity
import com.daovu65.employeemanager.edit.EditProfileVMFactory
import com.daovu65.employeemanager.profile.ProfileActivity
import com.daovu65.employeemanager.profile.ProfileVMFactory

object InjectionUtil {
    private val apiService by lazy {
        RetrofitFactory().getService()
    }

    private val repository by lazy {
        RepositoryImpl(apiService)
    }

    private val getAllEmployee by lazy {
        GetAllEmployee(repository)
    }

    private val getEmployeeById by lazy {
        GetEmployeeById(repository)
    }

    private val createEmployee by lazy {
        CreateEmployee(repository)
    }

    private val deleteEmployee by lazy {
        DeleteEmployee(repository)
    }

    private val updateEmployee by lazy {
        UpdateEmployee(repository)
    }

    fun inject(mainActivity: MainActivity) {
        val viewModelFactory = MainVMFactory(
            getAllEmployee = getAllEmployee
        )
        mainActivity.viewModelFactory = viewModelFactory
    }

    fun inject(profileActivity: ProfileActivity) {
        val viewModelFactory = ProfileVMFactory(
            getEmployeeById = getEmployeeById
        )
        profileActivity.viewModelFactory = viewModelFactory
    }

    fun inject(editProfileActivity: EditProfileActivity) {
        val viewModelFactory = EditProfileVMFactory(
            updateEmployee = updateEmployee,
            deleteEmployee = deleteEmployee,
            createEmployee = createEmployee,
            getEmployeeById = getEmployeeById
        )
        editProfileActivity.viewModelFactory = viewModelFactory
    }
}