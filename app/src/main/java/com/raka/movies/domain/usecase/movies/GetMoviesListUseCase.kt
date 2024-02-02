package com.raka.movies.domain.usecase.movies

import com.movies.data.CallResult
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase for getting a Movie List
 */
interface GetMoviesListUseCase {

    /**
     * get a list of Movie
     * @return a flow of Call result which contains a list of MovieItemCompact
     */
    fun getMoviesList(): Flow<CallResult<List<MovieItemCompact>>>
}

class GetMoviesListUseCaseImpl @Inject constructor(private val moviesRepository: MoviesRepository) :
    GetMoviesListUseCase {

    override fun getMoviesList(): Flow<CallResult<List<MovieItemCompact>>> =
        moviesRepository.getMoviesList()
}