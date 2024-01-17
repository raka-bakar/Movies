package com.raka.movies.data.repository

import android.util.Log
import com.raka.movies.data.CallResult
import com.raka.movies.data.DataSource
import com.raka.movies.data.db.DbMovieBookmark
import com.raka.movies.data.db.MovieBookmarkDao
import com.raka.movies.data.model.MovieItemCompact
import com.raka.movies.utils.toCompactList
import com.raka.movies.utils.toCompactStaffList
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
     * @return a flow of list of MoviesItem
     */
    fun getMoviesList(): Flow<CallResult<List<MovieItemCompact>>>

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
}

class MoviesRepositoryImpl @Inject constructor(
    private val dataSource: DataSource,
    private val dao: MovieBookmarkDao
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

    override fun getMoviesList(): Flow<CallResult<List<MovieItemCompact>>> =
        flow {
            coroutineScope {
                val list = dataSource.loadMoviesFromAssetFile()
                if (list.isEmpty()) {
                    emit(CallResult.Error("Data is empty", null))
                } else {
                    val compactList = list.toCompactList()
                    val compactStaffList = getStaffPicks()
                    val allMovieList = (compactStaffList + compactList).distinctBy(
                        MovieItemCompact::id
                    )

                    emit(CallResult.Success(updateBookmarkStatus(allMovieList)))
                }
            }
        }

    override fun getMovie(movieId: Int): Flow<CallResult<MovieItemCompact>> = flow {
        coroutineScope {
            getMoviesList().collect { result ->
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
        Log.e("movie","v ${movie.runtime}")
        dao.bookmarkMovie(DbMovieBookmark.toDbMovieBookmark(movie))
    }

    override fun getBookmarkedMovies(): Flow<CallResult<List<MovieItemCompact>>> = flow {
        coroutineScope {
            val listCompact = dao.getBookmarkedMovies().map {
                MovieItemCompact.toMovieItemCompact(it)
            }
            if (listCompact.isNotEmpty()) {
                emit(CallResult.Success(listCompact))
            } else {
                emit(CallResult.Error("Data is empty"))
            }
        }
    }

    override suspend fun unBookmarkMovie(movie: MovieItemCompact) {
        dao.deleteMovie(DbMovieBookmark.toDbMovieBookmark(movie))
    }

    private fun getStaffPicks(): List<MovieItemCompact> {
        val staffList = dataSource.loadStaffPicksFromAssetFile()
        return staffList.toCompactStaffList()
    }

    private fun updateBookmarkStatus(list: List<MovieItemCompact>): List<MovieItemCompact> {
        val bookmarkList = dao.getBookmarkedMovies()
        if (bookmarkList.isNotEmpty()) {
            bookmarkList.forEach { data ->
                list.find { it.id == data.movieId }?.isBookmarked = data.isBookmarked
            }
        }
        return list
    }
}