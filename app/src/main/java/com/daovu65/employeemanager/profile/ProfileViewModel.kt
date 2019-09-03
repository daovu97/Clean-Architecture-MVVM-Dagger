package com.daovu65.employeemanager.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daovu65.employeeManager.domain.entity.Employee
import com.daovu65.employeeManager.domain.interacter.GetEmployeeById
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getEmployeeById: GetEmployeeById
) : ViewModel() {

    private val _currentEmployeeId = MutableLiveData<String>()
    val currentEmployeeId: LiveData<String>
        get() = _currentEmployeeId

    private val _fullName = MutableLiveData<String>()
    val fullName: LiveData<String>
        get() = _fullName

    private val _age = MutableLiveData<String>()
    val age: LiveData<String>
        get() = _age

    private val _imageProfile = MutableLiveData<String>()
    val imageProfile: LiveData<String>
        get() = _imageProfile

    private val _salary = MutableLiveData<String>()
    val salary: LiveData<String>
        get() = _salary

    fun getEmployeeById(id: String) {
        viewModelScope.launch {
            getEmployeeById.invoke(id) { employee, throwable ->
                employee?.let {
                    _currentEmployeeId.postValue(it.id)
                    _fullName.postValue(it.name)
                    _age.postValue(it.age)
                    _imageProfile.postValue(it.profileImage)
                    _salary.postValue(it.salary)
                }

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}