package com.daovu65.employeemanager.injection

import androidx.appcompat.app.AppCompatActivity
import com.daovu65.employeeManager.data.repository.RepositoryImpl
import com.daovu65.employeeManager.data.service.RetrofitFactory
import com.daovu65.employeeManager.domain.interacter.*
import com.daovu65.employeemanager.Main.MainActivity
import com.daovu65.employeemanager.edit.EditProfileActivity
import com.daovu65.employeemanager.profile.ProfileActivity

object InjectionUtil {
    private lateinit var activity: AppCompatActivity
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

    private val viewModelFactory by lazy {
        ViewModelFactory(
            activity = activity,
            updateEmployee = updateEmployee,
            getEmployeeById = getEmployeeById,
            getAllEmployee = getAllEmployee,
            deleteEmployee = deleteEmployee,
            createEmployee = createEmployee
        )
    }

    fun inject(mainActivity: MainActivity) {
        activity = mainActivity
        mainActivity.viewModelFactory =
            viewModelFactory
    }

    fun inject(profileActivity: ProfileActivity) {
        activity = profileActivity
        profileActivity.viewModelFactory =
            viewModelFactory
    }

    fun inject(editProfileActivity: EditProfileActivity) {
        activity = editProfileActivity
        editProfileActivity.viewModelFactory =
            viewModelFactory
    }
}