package com.daovu65.employeemanager.Main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daovu65.employeeManager.domain.entity.Employee
import com.daovu65.employeeManager.domain.interacter.GetAllEmployee
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAllEmployee: GetAllEmployee
) : ViewModel() {

    private val _listEmployee = MutableLiveData<List<Employee>>()
    val listEmployee: LiveData<List<Employee>>
        get() = _listEmployee


    fun getAllEmployee() = viewModelScope.launch {
        getAllEmployee.invoke { list, throwable ->
            list?.let {
                _listEmployee.postValue(it)
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}