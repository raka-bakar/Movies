package com.raka.movies.domain.usecase.movies

import com.movies.data.CallResult
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase for getting a list of Movie which picked by the Staff
 */
interface GetStaffPickListUseCase {

    /**
     * get list of Staff pick of movies
     * @param tags list of MovieItemCompact
     */
    fun getStaffPickList(): Flow<CallResult<List<MovieItemCompact>>>
}

class GetStaffPickListUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) :
    GetStaffPickListUseCase {

    override fun getStaffPickList(): Flow<CallResult<List<MovieItemCompact>>> =
        moviesRepository.getStaffPicksFlow()
}