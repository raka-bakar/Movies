package com.raka.movies.di

import com.raka.movies.data.repository.MoviesRepository
import com.raka.movies.data.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindMoviesRepository(repo: MoviesRepositoryImpl): MoviesRepository
}