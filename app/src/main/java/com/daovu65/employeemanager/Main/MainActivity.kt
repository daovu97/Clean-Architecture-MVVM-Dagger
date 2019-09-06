package com.daovu65.employeemanager.Main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.daovu65.employeemanager.InjectionUtil
import com.daovu65.employeemanager.adapter.MainAdapter
import com.daovu65.employeemanager.R
import com.daovu65.employeemanager.ViewModelFactory
import com.daovu65.employeemanager.edit.EditProfileActivity
import com.daovu65.employeemanager.profile.ProfileActivity
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val BUNDLE_PROFILE_ID = "BUNDLE_PROFILE_ID"
        const val BUNDLE_EDIT_PROFILE = "BUNDLE_EDIT_PROFILE"
        const val BUNDLE_ADD_NEW = "BUNDLE_ADD_NEW"
    }

    private lateinit var viewModel: MainViewModel
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var mAdapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        InjectionUtil.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = viewModelFactory.create(MainViewModel::class.java)
        initView()
        searchByName()
        swipeToRefresh()

    }

    private fun initView() {
        StatusBarUtil.setTranslucent(this, 30)
        recycler_container.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            mAdapter = MainAdapter(this@MainActivity) {
                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                intent.putExtra(BUNDLE_PROFILE_ID, mAdapter.getEmployeeAt(it).id)
                startActivity(intent)
            }
            adapter = mAdapter
            viewModel.getAllEmployee()
            viewModel.listEmployee.observe(this@MainActivity, Observer {
                if (it == null) Toast.makeText(
                    this@MainActivity,
                    "No employee, please check internet!",
                    Toast.LENGTH_SHORT
                ).show()
                else mAdapter.submitValue(it)
            })
        }
        btn_add_new.setOnClickListener {
            val intent = Intent(this@MainActivity, EditProfileActivity::class.java)
            intent.putExtra(BUNDLE_EDIT_PROFILE, BUNDLE_ADD_NEW)
            startActivity(intent)
        }
    }

    private fun swipeToRefresh() {
        swiperefresh.setOnRefreshListener {
            GlobalScope.launch(Dispatchers.IO) {
                viewModel.getAllEmployee()

                withContext(Dispatchers.Main) {
                    viewModel.refreshState.observe(this@MainActivity, Observer {
                        if (it == false && swiperefresh.isRefreshing) swiperefresh.isRefreshing =
                            false
                    })
                }
            }

        }
    }

    private fun searchByName() {
        viewModel.numberEmployee.observe(this, Observer {
            it?.let {
                edt_search.hint = it
            }
        })
        edt_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0.isNullOrEmpty()) viewModel.getAllEmployee()
                else viewModel.searchEmployeeByName(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllEmployee()
    }
}
