package com.daovu65.employeemanager.ui.edit

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daovu65.employeemanager.domain.entity.Employee
import com.daovu65.employeemanager.domain.interacter.CreateEmployee
import com.daovu65.employeemanager.domain.interacter.DeleteEmployee
import com.daovu65.employeemanager.domain.interacter.GetEmployeeById
import com.daovu65.employeemanager.domain.interacter.UpdateEmployee
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

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

    val firstName = MutableLiveData<String>()

    private val _currentEmployeeId = MutableLiveData<String>()
    val currentEmployeeId: LiveData<String>
        get() = _currentEmployeeId

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
                    _liveEmployee.postValue(it)
                    _currentEmployeeId.postValue(it.id)
                    fullName.postValue(it.name)
                    age.postValue(it.age)
                    _imageProfile.postValue(it.profileImage)
                    salary.postValue(it.salary)
                }

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }


    fun setState(instate: Int) {
        _state.value = instate
    }


}