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
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import com.jaeger.library.StatusBarUtil


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
        StatusBarUtil.setTranslucent(this, 30)

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
            updateDialog()
            viewModel.updateEmployee()

        }
    }

    private fun addNewStudent() {
        btn_delete.visibility = View.GONE
        btn_save.setOnClickListener {
            viewModel.createEmployee()
            progressDialog("Creating",
                onCancelClick = {
                    viewModel.cancelJob()
                },
                onSuccess = { dialog ->
                    viewModel.stateAddNewDialog.observe(this, Observer {
                        it?.let {
                            dialog.dismiss()
                            alertResultDialog(it) {
                                it.dismiss()
                                this.finish()
                            }
                        }

                    })
                })

        }
    }


    private fun deleteDialog() {
        alertDialog("Delete employee! Are you sure??",
            onYesClick = {
                progressDialog("Deleting",
                    onSuccess = { dialog ->
                        viewModel.deleteEmployee()
                        viewModel.stateDeleteDialog.observe(this, Observer {
                            it?.let {
                                dialog.dismiss()
                                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                                val intent =
                                    Intent(this@EditProfileActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                this.startActivity(intent)
                                this.finish()
                            }
                        })
                    },
                    onCancelClick = {
                        viewModel.cancelJob()
                    })
            },
            onNoClick = {
                it.dismiss()
            })
    }

    private fun updateDialog() {
        progressDialog("Updating",
            onSuccess = { dialog ->
                viewModel.stateUpdateDialog.observe(this, Observer {
                    it?.let {
                        dialog.dismiss()
                        alertResultDialog(it) {
                            it.dismiss()
                            this.finish()
                        }
                    }
                })
            },
            onCancelClick = {
                viewModel.cancelJob()
                it.dismiss()
            })


    }

    private fun progressDialog(
        msg: String,
        onSuccess: (DialogInterface) -> Unit,
        onCancelClick: (DialogInterface) -> Unit
    ) {
        val myDialog = ProgressDialog(this)
        myDialog.setMessage("$msg...")
        myDialog.setCancelable(false)
        myDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel") { dialog, _ ->
            onCancelClick(dialog)
        }
        myDialog.show()
        onSuccess(myDialog)
    }

    private fun alertDialog(
        msg: String,
        onYesClick: (DialogInterface) -> Unit,
        onNoClick: (DialogInterface) -> Unit
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(msg)
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            onYesClick(dialog)
        }

        builder.setNegativeButton("No") { dialog, _ ->
            onNoClick(dialog)
        }

        val alert11 = builder.create()
        alert11.show()
    }

    private fun alertResultDialog(
        msg: String,
        onYesClick: (DialogInterface) -> Unit
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(msg)
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            onYesClick(dialog)
        }

        val alert11 = builder.create()
        alert11.show()
    }
}
