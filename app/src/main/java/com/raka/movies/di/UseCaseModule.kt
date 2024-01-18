package com.raka.movies.di

import com.raka.movies.domain.usecase.BookmarkUseCase
import com.raka.movies.domain.usecase.BookmarkUseCaseImpl
import com.raka.movies.domain.usecase.MoviesUseCase
import com.raka.movies.domain.usecase.MoviesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {
    @Binds
    fun bindMoviesUseCase(useCase: MoviesUseCaseImpl): MoviesUseCase

    @Binds
    fun bindBookmarkUseCase(useCase: BookmarkUseCaseImpl): BookmarkUseCase
}