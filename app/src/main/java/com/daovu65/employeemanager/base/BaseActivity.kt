package com.daovu65.employeemanager.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

abstract class BaseActivity<T : ViewModel> : AppCompatActivity() {

    private var viewModel: T? = null

    abstract fun getViewModel(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel = if (viewModel == null) getViewModel() else viewModel
    }

}