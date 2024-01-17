package com.raka.movies.domain.usecase

import com.raka.movies.data.CallResult
import com.raka.movies.data.model.MovieItemCompact
import com.raka.movies.data.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase for Staff Picks of Movie page
 */
interface StaffPicksUseCase {
    /**
     * get list of Staff Picks of Movie
     * @param tags list of MovieItemCompact
     */
    fun getStaffPicks(): Flow<CallResult<List<MovieItemCompact>>>
}

class StaffPicksUseCaseImpl @Inject constructor(private val moviesRepository: MoviesRepository) :
    StaffPicksUseCase {

    override fun getStaffPicks(): Flow<CallResult<List<MovieItemCompact>>> =
        moviesRepository.getStaffPicksFlow()
}