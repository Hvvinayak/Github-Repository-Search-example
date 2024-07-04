package com.example.githubrepository.di

import com.example.githubrepository.data.repository.GithubRepository
import com.example.githubrepository.domain.IGithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindsGithubRepository(githubRepository: GithubRepository): IGithubRepository
}