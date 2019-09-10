package com.daovu65.employeemanager.injection.module

import com.daovu65.employeeManager.data.repository.RepositoryImpl
import com.daovu65.employeeManager.domain.repository.Repository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}