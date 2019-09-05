package com.daovu65.employeemanager.Main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daovu65.employeeManager.domain.entity.Employee
import com.daovu65.employeeManager.domain.interacter.GetAllEmployee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAllEmployee: GetAllEmployee
) : ViewModel() {

    private val _refreshState = MutableLiveData<Boolean>()
    val refreshState: LiveData<Boolean>
        get() = _refreshState

    private val _listEmployee = MutableLiveData<List<Employee>>()
    val listEmployee: LiveData<List<Employee>>
        get() = _listEmployee

    fun searchEmployeeByName(name: String) {
        viewModelScope.launch {
            getAllEmployee.invoke { list, throwable ->
                launch(Dispatchers.Default) {
                    list?.let {
                        _listEmployee.postValue(it.filter { employee ->
                            employee.name == name
                        })
                    }
                }


            }
        }
    }

    fun getAllEmployee() = viewModelScope.launch {
        getAllEmployee.invoke { list, throwable ->
            list?.let {
                _listEmployee.postValue(it)

            }
            _refreshState.postValue(false)
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
        _refreshState.value = null
        super.onCleared()

    }
}