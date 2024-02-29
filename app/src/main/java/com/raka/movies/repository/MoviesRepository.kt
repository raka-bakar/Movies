package com.raka.movies.repository

import com.movies.DataProvider
import com.movies.data.CallResult
import com.movies.data.model.StaffPicksItem
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.utils.Utils
import com.raka.movies.utils.Utils.toDbMovieBookmark
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Repository to provide Movies data
 */
interface MoviesRepository {
    /**
     * get a list of Staff Picks of MOvies and return it as a Flow
     * @return a flow of list of StaffPicksItem
     */
    fun getStaffPicksFlow(): Flow<CallResult<List<MovieItemCompact>>>

    /**
     * get a list of All Movies
     ** @param searchText to search title of movie
     * @return a flow of list of MoviesItem
     */
    fun getMoviesList(searchText: String): Flow<CallResult<List<MovieItemCompact>>>

    /**
     * get a list of Staff Picks of Movies
     * @return a list of MovieItemCompact
     */
    fun getStaffPicks(): List<MovieItemCompact>

    /**
     * get a movie detail based on movie Id
     * @param movieId id of the movie
     * @return a flow of MovieItemCompact
     */
    fun getMovie(movieId: Int): Flow<CallResult<MovieItemCompact>>

    /**
     * Add a bookmark to a movie
     *  @param movie an object of MovieItemCompact
     */
    suspend fun addBookmark(movie: MovieItemCompact)

    /**
     * Add a bookmark to a movie
     *  @return a flow of list of MovieItemCompact
     */
    fun getBookmarkedMovies(): Flow<CallResult<List<MovieItemCompact>>>

    /**
     * Remove bookmark status of a movie
     *  @param movie an object of MovieItemCompact
     */
    suspend fun unBookmarkMovie(movie: MovieItemCompact)

    /**
     * Get bookmarkstatus from DB and update the movieslist
     *  @param list an object of MovieItemCompact
     *  @return a list of MovieItemCompact with updated bookmark status
     */
    suspend fun updateBookmarkStatus(list: List<MovieItemCompact>): List<MovieItemCompact>
}

class MoviesRepositoryImpl @Inject constructor(
    private val dataProvider: DataProvider
) :
    MoviesRepository {
    override fun getStaffPicksFlow(): Flow<CallResult<List<MovieItemCompact>>> = flow {
        coroutineScope {
            val compactStaffList = getStaffPicks()
            if (compactStaffList.isEmpty()) {
                emit(CallResult.Error("Data is empty", null))
            } else {
                emit(CallResult.Success(updateBookmarkStatus(compactStaffList)))
            }
        }
    }

    override fun getMoviesList(searchText: String): Flow<CallResult<List<MovieItemCompact>>> =
        flow {
            coroutineScope {
                val list = dataProvider.getMovies()

                if (list.isEmpty()) {
                    emit(CallResult.Error("Data is empty", null))
                } else {
                    val compactList = Utils.toCompactList(list)
                    val compactStaffList = getStaffPicks()
                    var allMovieList = (compactStaffList + compactList).distinctBy(
                        MovieItemCompact::id
                    )

                    if (searchText.isNotBlank()) {
                        allMovieList = allMovieList.filter { movie ->
                            movie.title.lowercase().contains(searchText.trim().lowercase())
                        }
                    }

                    emit(CallResult.Success(updateBookmarkStatus(allMovieList)))
                }
            }
        }

    override fun getMovie(movieId: Int): Flow<CallResult<MovieItemCompact>> = flow {
        coroutineScope {
            getMoviesList("").collect { result ->
                if (result is CallResult.Success) {
                    val item = result.data?.find { it.id == movieId }
                    if (item != null) {
                        emit(CallResult.Success(item))
                    } else {
                        emit(CallResult.Error("Data not found"))
                    }
                } else {
                    emit(CallResult.Error("Data not found"))
                }
            }
        }
    }

    override suspend fun addBookmark(movie: MovieItemCompact) {
        dataProvider.addBookmarkedMovie(toDbMovieBookmark(movie))
    }

    override fun getBookmarkedMovies(): Flow<CallResult<List<MovieItemCompact>>> = flow {
        coroutineScope {
            dataProvider.getBookmarkedMovies().collect { result ->
                if (result is CallResult.Success) {
                    val compactList = result.data?.map { Utils.toMovieItemCompact(it) }
                    if (!compactList.isNullOrEmpty()) {
                        emit(CallResult.Success(compactList))
                    } else {
                        emit(CallResult.Error("Data not found"))
                    }
                } else {
                    emit(CallResult.Error("Data not found"))
                }
            }
        }
    }

    override suspend fun unBookmarkMovie(movie: MovieItemCompact) {
        dataProvider.deleteBookmarkedMovie(toDbMovieBookmark(movie))
    }

    override fun getStaffPicks(): List<MovieItemCompact> {
        val staffList: List<StaffPicksItem> = dataProvider.getStaffPick()
        return Utils.toCompactStaffList(staffList)
    }

    override suspend fun updateBookmarkStatus(list: List<MovieItemCompact>):
            List<MovieItemCompact> {
        dataProvider.getBookmarkedMovies().collect { result ->
            if (result is CallResult.Success) {
                if (result.data?.isNotEmpty() == true) {
                    result.data?.forEach { data ->
                        list.find { it.id == data.movieId }?.isBookmarked = data.isBookmarked
                    }
                }
            }
        }
        return list
    }
}