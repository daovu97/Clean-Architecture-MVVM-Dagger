package com.daovu65.employeemanager.ui.edit

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.daovu65.employeemanager.InjectionUtil
import com.daovu65.employeemanager.R
import com.daovu65.employeemanager.databinding.ActivityEditProfileBinding
import com.daovu65.employeemanager.ui.Main.MainActivity
import com.daovu65.employeemanager.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.btn_back

class EditProfileActivity : AppCompatActivity() {


    private lateinit var viewModel: EditProfileViewModel
    lateinit var viewModelFactory: EditProfileVMFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        InjectionUtil.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = viewModelFactory.create(EditProfileViewModel::class.java)
        val binding: ActivityEditProfileBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        intent.extras?.let { bundle ->
            when (bundle.get(MainActivity.BUNDLE_EDIT_PROFILE)) {
                MainActivity.BUNDLE_ADD_NEW -> viewModel.setState(1)
                ProfileActivity.BUNDLE_EDIT_PROFILE -> viewModel.setState(2)
            }
        }

        btn_back.setOnClickListener {
            finish()
        }

        viewModel.state.observe(this, androidx.lifecycle.Observer {
            when (it) {
                1 -> addNewStudent()
                2 -> editStudentProfile()
            }
        })
    }

    private fun editStudentProfile() {
        intent.extras?.let {
            it.getString(ProfileActivity.BUNDLE_PROFILE_ID)?.let { id ->
                viewModel.getEmployeeById(id)
            }

        }

        viewModel.liveEmployee.observe(this, androidx.lifecycle.Observer {
            Glide.with(this@EditProfileActivity)
                .load(it.profileImage)
                .error(android.R.drawable.ic_menu_add)
                .into(img_profile_edit)

        })
    }

    private fun addNewStudent() {
        btn_delete.visibility = View.GONE
    }
}
