package com.daovu65.employeemanager.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.daovu65.employeemanager.Main.MainActivity
import com.daovu65.employeemanager.R
import com.daovu65.employeemanager.base.BaseActivity
import com.daovu65.employeemanager.databinding.ActivityProfileBinding
import com.daovu65.employeemanager.edit.EditProfileActivity
import com.daovu65.employeemanager.injection.DaggerMyComponent
import com.daovu65.employeemanager.injection.ViewModelFactory
import kotlinx.android.synthetic.main.activity_profile.*
import javax.inject.Inject

class ProfileActivity : BaseActivity<ProfileViewModel>() {

    companion object {
        const val BUNDLE_EDIT_PROFILE = "BUNDLE_EDIT_PROFILE"
        const val BUNDLE_PROFILE_ID = "BUNDLE_PROFILE_ID"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private var currentStudentId: String? = null

    override fun getViewModel(): ProfileViewModel {
        DaggerMyComponent.builder().build().inject(this)
        return viewModelFactory.create(ProfileViewModel::class.java)
    }
    private val viewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding: ActivityProfileBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_profile)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initView()
    }

    private fun initView() {
        intent.extras?.let { bundle ->
            currentStudentId = bundle.getString(MainActivity.BUNDLE_PROFILE_ID)
            currentStudentId?.let {
                viewModel.getEmployeeById(it)
                viewModel.imageProfile.observe(this, Observer { imagePath ->
                    Glide.with(this@ProfileActivity)
                        .load(imagePath)
                        .error(R.mipmap.ic_launcher_round)
                        .into(img_profile)

                })
            }

        }

        btn_back.setOnClickListener {
            finish()
        }

        btn_edit.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            intent.putExtra(
                MainActivity.BUNDLE_EDIT_PROFILE,
                BUNDLE_EDIT_PROFILE
            )
            intent.putExtra(BUNDLE_PROFILE_ID, currentStudentId)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        currentStudentId?.let {
            viewModel.getEmployeeById(it)
        }

    }

}
