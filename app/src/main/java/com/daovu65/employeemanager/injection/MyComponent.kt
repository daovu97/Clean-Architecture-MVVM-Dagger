package com.daovu65.employeemanager.injection

import com.daovu65.employeemanager.Main.MainActivity
import com.daovu65.employeemanager.edit.EditProfileActivity
import com.daovu65.employeemanager.injection.module.ApiModule
import com.daovu65.employeemanager.injection.module.RepositoryModule
import com.daovu65.employeemanager.injection.module.ViewModelModule
import com.daovu65.employeemanager.profile.ProfileActivity
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        ApiModule::class]
)
interface MyComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: ProfileActivity)
    fun inject(activity: EditProfileActivity)
}