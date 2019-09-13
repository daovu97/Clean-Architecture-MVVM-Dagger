package com.daovu65.employeemanager.Main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.daovu65.employeemanager.R
import com.daovu65.employeemanager.adapter.MainAdapter
import com.daovu65.employeemanager.base.BaseActivity
import com.daovu65.employeemanager.edit.EditProfileActivity
import com.daovu65.employeemanager.injection.DaggerMyComponent
import com.daovu65.employeemanager.injection.ViewModelFactory
import com.daovu65.employeemanager.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel>() {


    companion object {
        const val BUNDLE_PROFILE_ID = "BUNDLE_PROFILE_ID"
        const val BUNDLE_EDIT_PROFILE = "BUNDLE_EDIT_PROFILE"
        const val BUNDLE_ADD_NEW = "BUNDLE_ADD_NEW"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var mAdapter: MainAdapter

    override fun getViewModel(): MainViewModel {
        DaggerMyComponent.builder().build().inject(this)
        return viewModelFactory.create(MainViewModel::class.java)

    }

    private val viewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        searchByName()
        swipeToRefresh()

    }

    private fun initView() {
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
