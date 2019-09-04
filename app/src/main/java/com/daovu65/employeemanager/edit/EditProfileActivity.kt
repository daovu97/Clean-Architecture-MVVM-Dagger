package com.daovu65.employeemanager.edit

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.daovu65.employeemanager.InjectionUtil
import com.daovu65.employeemanager.databinding.ActivityEditProfileBinding
import com.daovu65.employeemanager.Main.MainActivity
import com.daovu65.employeemanager.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_profile.btn_back
import androidx.appcompat.app.AlertDialog
import android.content.DialogInterface
import android.app.ProgressDialog
import androidx.lifecycle.Observer


class EditProfileActivity : AppCompatActivity() {


    private lateinit var viewModel: EditProfileViewModel
    lateinit var viewModelFactory: EditProfileVMFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        InjectionUtil.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = viewModelFactory.create(EditProfileViewModel::class.java)
        val binding: ActivityEditProfileBinding =
            DataBindingUtil.setContentView(
                this,
                com.daovu65.employeemanager.R.layout.activity_edit_profile
            )

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

        btn_delete.setOnClickListener {
            deleteDialog()
        }

        btn_save.setOnClickListener {
            viewModel.updateEmployee()
            updateDialog()
        }
    }

    private fun addNewStudent() {
        btn_delete.visibility = View.GONE
        btn_save.setOnClickListener {
            viewModel.createEmployee()
            addNewDialog()
        }
    }

    private fun addNewDialog() {
        val myDialog = ProgressDialog(this)
        myDialog.setMessage("Creating...")
        myDialog.setCancelable(false)
        myDialog.show()

        viewModel.stateAddNewDialog.observe(this, Observer {
            if (it == false && myDialog.isShowing) {
                myDialog.dismiss()
                this.finish()
            }
        })
    }

    private fun deleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete employee! Are you sure??")
        builder.setCancelable(true)

        builder.setPositiveButton("Yes") { dialog, id ->
            viewModel.deleteEmployee()
            this.finish()
        }

        builder.setNegativeButton("No") { dialog, id ->
            dialog.cancel()
        }

        val alert11 = builder.create()
        alert11.show()
    }

    private fun updateDialog() {
        val myDialog = ProgressDialog(this)
        myDialog.setMessage("Updating...")
        myDialog.setCancelable(false)
        myDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE, "Cancel"
        ) { dialog, which ->
            viewModel.cancelJob()
            dialog.dismiss()
        }
        myDialog.show()

        viewModel.stateProgressDialog.observe(this, Observer {
            if (it == false && myDialog.isShowing) {
                myDialog.dismiss()
                this.finish()
            }
        })
    }
}
