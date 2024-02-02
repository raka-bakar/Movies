package com.raka.movies.domain.usecase.bookmark

import com.movies.data.CallResult
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase for loading a list of Bookmarked Movies
 */
interface GetBookmarkedMovies {

    /**
     * adding movie as a new bookmarked movie
     * @return a flow of Call Result which contain List of MovieItemCompact
     */
    fun getBookmarkedMovies(): Flow<CallResult<List<MovieItemCompact>>>
}

class GetBookmarkedMoviesImpl @Inject constructor(private val movieRepository: MoviesRepository):
    GetBookmarkedMovies {
    override fun getBookmarkedMovies(): Flow<CallResult<List<MovieItemCompact>>> {
        return movieRepository.getBookmarkedMovies()
    }
}