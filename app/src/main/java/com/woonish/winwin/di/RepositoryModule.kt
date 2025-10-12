package com.woonish.winwin.di

import com.woonish.winwin.data.repository.SportsRepository
import com.woonish.winwin.data.repository.SportsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSportsRepository(impl: SportsRepositoryImpl): SportsRepository
}


