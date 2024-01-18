package com.raka.movies.domain.usecase

import com.movies.data.CallResult
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface BookmarkUseCase {
    suspend fun bookmarkMovie(movie: MovieItemCompact)
    fun getBookmarkedMovies(): Flow<CallResult<List<MovieItemCompact>>>
    suspend fun unBookmarkMovie(movie: MovieItemCompact)
}

class BookmarkUseCaseImpl @Inject constructor(private val movieRepository: MoviesRepository) :
    BookmarkUseCase {
    override suspend fun bookmarkMovie(movie: MovieItemCompact) {
        movieRepository.addBookmark(movie)
    }

    override fun getBookmarkedMovies(): Flow<CallResult<List<MovieItemCompact>>> {
        return movieRepository.getBookmarkedMovies()
    }

    override suspend fun unBookmarkMovie(movie: MovieItemCompact) {
        movieRepository.unBookmarkMovie(movie)
    }
}