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
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAllEmployee: GetAllEmployee
) : ViewModel() {

    private val _refreshState = MutableLiveData<Boolean>()
    val refreshState: LiveData<Boolean>
        get() = _refreshState

    private val _listEmployee = MutableLiveData<List<Employee>>()
    val listEmployee: LiveData<List<Employee>>
        get() = _listEmployee

    private val _numberEmployee = MutableLiveData<String>()
    val numberEmployee: LiveData<String>
        get() = _numberEmployee

    fun searchEmployeeByName(name: String) {
        viewModelScope.launch {
            getAllEmployee.invoke { list, _ ->
                list?.let {
                    launch(Dispatchers.Default) {
                        val value = it.filter { employee ->
                            employee.name == name
                        }
                        _listEmployee.postValue(value)

                    }
                }

            }
        }
    }

    fun getAllEmployee() = viewModelScope.launch {
        getAllEmployee.invoke { list, throwable ->
            list?.let {
                _listEmployee.postValue(it)
                _numberEmployee.postValue("Search: ${it.size} employee")
            }

            throwable?.let {
                _listEmployee.postValue(null)
                _numberEmployee.postValue("Search")
            }
            _refreshState.postValue(false)
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
        _refreshState.value = null
        _numberEmployee.value = null
        super.onCleared()
    }
}