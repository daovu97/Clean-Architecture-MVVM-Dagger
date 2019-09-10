package com.daovu65.employeemanager.injection

import com.daovu65.employeemanager.Main.MainActivity
import com.daovu65.employeemanager.edit.EditProfileActivity
import com.daovu65.employeemanager.injection.module.ApiModule
import com.daovu65.employeemanager.injection.module.RepositoryModule
import com.daovu65.employeemanager.injection.module.ViewModelModule
import com.daovu65.employeemanager.profile.ProfileActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, RepositoryModule::class, ApiModule::class])
interface MyComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: ProfileActivity)
    fun inject(activity: EditProfileActivity)
}