package com.raka.movies.di

import com.raka.movies.domain.usecase.bookmark.BookmarkMovieUseCase
import com.raka.movies.domain.usecase.bookmark.BookmarkMovieUseCaseImpl
import com.raka.movies.domain.usecase.bookmark.GetBookmarkedMovies
import com.raka.movies.domain.usecase.bookmark.GetBookmarkedMoviesImpl
import com.raka.movies.domain.usecase.bookmark.UnbookmarkMovieUseCase
import com.raka.movies.domain.usecase.bookmark.UnbookmarkMovieUseCaseImpl
import com.raka.movies.domain.usecase.movies.GetMovieUseCase
import com.raka.movies.domain.usecase.movies.GetMovieUseCaseImpl
import com.raka.movies.domain.usecase.movies.GetMoviesListUseCase
import com.raka.movies.domain.usecase.movies.GetMoviesListUseCaseImpl
import com.raka.movies.domain.usecase.movies.GetStaffPickListUseCase
import com.raka.movies.domain.usecase.movies.GetStaffPickListUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindBookmarkMovieUseCase(usecase: BookmarkMovieUseCaseImpl): BookmarkMovieUseCase

    @Binds
    fun bindGetBookmarkedMoviesUseCase(usecase: GetBookmarkedMoviesImpl): GetBookmarkedMovies

    @Binds
    fun bindUnbookmarkMovieUseCase(useCase: UnbookmarkMovieUseCaseImpl): UnbookmarkMovieUseCase

    @Binds
    fun bindGetStaffPickListMovieUseCase(useCase: GetStaffPickListUseCaseImpl):
            GetStaffPickListUseCase

    @Binds
    fun bindGetMovieListUseCase(useCase: GetMoviesListUseCaseImpl): GetMoviesListUseCase

    @Binds
    fun bindGetMovieUseCase(useCase: GetMovieUseCaseImpl): GetMovieUseCase
}