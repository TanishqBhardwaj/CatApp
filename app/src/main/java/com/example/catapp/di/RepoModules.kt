package com.example.catapp.di

import com.example.catapp.data.repositories.CatRepository
import com.example.catapp.data.repositories.ICatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface RepoModules {

    @Binds
    fun bindICatRepo(catRepository: CatRepository): ICatRepository
}