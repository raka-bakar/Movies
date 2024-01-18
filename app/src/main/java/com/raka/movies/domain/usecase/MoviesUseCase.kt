package com.raka.movies.domain.usecase

import com.movies.data.CallResult
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase for Movies List
 */
interface MoviesUseCase {
    /**
     * get list of Movies
     * @param tags list of MovieItemCompact
     */
    fun getMoviesList(): Flow<CallResult<List<MovieItemCompact>>>

    /**
     * get list of Staff pick of movies
     * @param tags list of MovieItemCompact
     */
    fun getStaffPickList(): Flow<CallResult<List<MovieItemCompact>>>

    /**
     * get a movie detail based on movie Id
     * @param movieId id of the movie
     * @return a flow of MovieItemCompact
     */
    fun getMovie(movieId: Int): Flow<CallResult<MovieItemCompact>>
}

class MoviesUseCaseImpl @Inject constructor(private val moviesRepository: MoviesRepository) :
    MoviesUseCase {
    override fun getMoviesList(): Flow<CallResult<List<MovieItemCompact>>> =
        moviesRepository.getMoviesList()

    override fun getStaffPickList(): Flow<CallResult<List<MovieItemCompact>>> =
        moviesRepository.getStaffPicksFlow()

    override fun getMovie(movieId: Int): Flow<CallResult<MovieItemCompact>> =
        moviesRepository.getMovie(movieId = movieId)
}