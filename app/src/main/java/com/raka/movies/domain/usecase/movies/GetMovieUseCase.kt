package com.raka.movies.domain.usecase.movies

import com.movies.data.CallResult
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase for getting a movie
 */
interface GetMovieUseCase {
    /**
     * get a movie detail based on movie Id
     * @param movieId id of the movie
     * @return a flow of MovieItemCompact
     */
    fun getMovie(movieId: Int): Flow<CallResult<MovieItemCompact>>
}

class GetMovieUseCaseImpl @Inject constructor(private val moviesRepository: MoviesRepository) :
    GetMovieUseCase {
    override fun getMovie(movieId: Int): Flow<CallResult<MovieItemCompact>> =
        moviesRepository.getMovie(movieId = movieId)
}