package com.daovu65.employeemanager.edit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daovu65.employeeManager.domain.entity.Employee
import com.daovu65.employeeManager.domain.interacter.CreateEmployee
import com.daovu65.employeeManager.domain.interacter.DeleteEmployee
import com.daovu65.employeeManager.domain.interacter.GetEmployeeById
import com.daovu65.employeeManager.domain.interacter.UpdateEmployee
import kotlinx.coroutines.*

class EditProfileViewModel(
    private val updateEmployee: UpdateEmployee,
    private val deleteEmployee: DeleteEmployee,
    private val createEmployee: CreateEmployee,
    private val getEmployeeById: GetEmployeeById
) : ViewModel() {

    private var currentEmployee: Employee? = null

    private val _liveEmployee = MutableLiveData<Employee>()
    val liveEmployee: LiveData<Employee>
        get() = _liveEmployee

    private val _state = MutableLiveData<Int>()
    val state: LiveData<Int>
        get() = _state

    private val _stateUpdateDialog = MutableLiveData<String>()
    val stateUpdateDialog: LiveData<String>
        get() = _stateUpdateDialog

    private val _stateAddNewDialog = MutableLiveData<String>()
    val stateAddNewDialog: LiveData<String>
        get() = _stateAddNewDialog

    private val _stateDeleteDialog = MutableLiveData<String>()
    val stateDeleteDialog: LiveData<String>
        get() = _stateDeleteDialog

    val fullName = MutableLiveData<String>()

    val age = MutableLiveData<String>()

    private val _imageProfile = MutableLiveData<String>()
    val imageProfile: LiveData<String>
        get() = _imageProfile

    val salary = MutableLiveData<String>()

    fun getEmployeeById(id: String) {
        viewModelScope.launch {
            getEmployeeById.invoke(id) { employee, throwable ->
                employee?.let {
                    currentEmployee = it
                    _liveEmployee.postValue(it)
                    fullName.postValue(it.name)
                    age.postValue(it.age)
                    _imageProfile.postValue(it.profileImage)
                    salary.postValue(it.salary)
                }

            }
        }
    }

    fun createEmployee() {
        val inputName = fullName.value ?: ""
        if (inputName.isBlank() || inputName.isEmpty()) return
        val inputAge = age.value ?: "0"
        val inputSalary = salary.value ?: ""
        val newEmployee = Employee(
            id = null,
            name = inputName,
            age = inputAge,
            salary = inputSalary,
            profileImage = null
        )
        viewModelScope.launch {
            delay(1000L)
            createEmployee.invoke(newEmployee) { employee, throwable ->
                Log.d("Creat", employee.toString())
                _stateAddNewDialog.postValue("name:${employee?.name}\nage:${employee?.age}\nsalary:${employee?.salary}")
            }
        }

    }

    fun updateEmployee() {
        var newEmployee: Employee? = null
        val inputName = fullName.value ?: ""
        if (inputName.isBlank() || inputName.isEmpty()) return
        val inputAge = age.value ?: "0"
        val inputSalary = salary.value ?: ""
        currentEmployee?.let {
            newEmployee = Employee(
                id = it.id,
                name = inputName,
                age = inputAge,
                salary = inputSalary,
                profileImage = null
            )
        }

        newEmployee?.let {
            viewModelScope.launch {
                delay(1000L)
                updateEmployee.invoke(it) { employee, throwable ->
                    Log.d("Creat", employee.toString())
                    _stateUpdateDialog.postValue("name:${employee?.name}\nage:${employee?.age}\nsalary:${employee?.salary}")
                }
            }
        }


    }


    fun deleteEmployee() {
        viewModelScope.launch {
            delay(1000L)
            currentEmployee?.let {
                deleteEmployee.invoke(it.id!!) { success, error ->
                    _stateDeleteDialog.postValue(success)
                }
            }
        }

    }

    fun cancelJob() {
        viewModelScope.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
        _state.value = 0
        _stateAddNewDialog.value = null
        _stateUpdateDialog.value = null
        _stateDeleteDialog.value = null
    }


    fun setState(instate: Int) {
        _state.value = instate
    }


}