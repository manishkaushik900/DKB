package com.manish.dkb.di

import com.manish.dkb.data.repository.AlbumRepositoryImpl
import com.manish.dkb.domain.repository.AlbumRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindScheduleRepository(albumRepositoryImpl: AlbumRepositoryImpl): AlbumRepository

}