package com.daovu65.employeemanager.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daovu65.employeemanager.Main.MainViewModel
import com.daovu65.employeemanager.edit.EditProfileViewModel
import com.daovu65.employeemanager.injection.ViewModelFactory
import com.daovu65.employeemanager.injection.util.ViewModelKey
import com.daovu65.employeemanager.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface ViewModelModule {
    // PROVIDE YOUR OWN MODELS HERE
    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(EditProfileViewModel::class)
    fun bindEditProfileViewModel(editProfileViewModel: EditProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @Singleton
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
