package com.movies

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.movies.data.CallResult
import com.movies.data.db.DbMovieBookmark
import com.movies.data.db.MovieBookmarkDao
import com.movies.data.model.MoviesItem
import com.movies.data.model.StaffPicksItem
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * DataSource to fetch data from a local or network source
 */
interface DataSource {
    /**
     * Load Json of Staff Picks of Movies from Asset File
     * @return Flow of list of StaffPicksItem
     */
    fun loadStaffPicksFromAssetFile(): List<StaffPicksItem>

    /**
     * Load Json of Movies list from Asset File
     * @return Flow of list of MoviesItem
     */
    fun loadMoviesFromAssetFile(): List<MoviesItem>

    /**
     * Add a bookmark to a movie
     *  @param movie an object of MovieItemCompact
     */
    suspend fun addBookmark(movie: DbMovieBookmark)

    /**
     * Add a bookmark to a movie
     *  @return a flow of list of MovieItemCompact
     */
    fun getBookmarkedMovies(): Flow<CallResult<List<DbMovieBookmark>>>

    /**
     * Remove bookmark status of a movie
     *  @param movie an object of MovieItemCompact
     */
    suspend fun unBookmarkMovie(movie: DbMovieBookmark)
}

class DataSourceImpl @Inject constructor(
    private val context: Context,
    private val dao: MovieBookmarkDao
) : DataSource {
    override fun loadMoviesFromAssetFile(): List<MoviesItem> {
        val jsonString: String
        try {
            jsonString = context.assets.open("movies.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Timber.e(ioException)
            return emptyList()
        }

        val listMovies = object : TypeToken<List<MoviesItem>>() {}.type
        return Gson().fromJson(jsonString, listMovies)
    }

    override fun loadStaffPicksFromAssetFile(): List<StaffPicksItem> {
        val jsonString: String
        try {
            jsonString = context.assets.open("staff_picks.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Timber.e(ioException)
            return emptyList()
        }

        val listMovies = object : TypeToken<List<StaffPicksItem>>() {}.type
        return Gson().fromJson(jsonString, listMovies)
    }

    override suspend fun addBookmark(movie: DbMovieBookmark) {
        dao.bookmarkMovie(movie)
    }

    override fun getBookmarkedMovies(): Flow<CallResult<List<DbMovieBookmark>>> = flow {
        coroutineScope {
            val listBookmarkedMovies = dao.getBookmarkedMovies()
            if (listBookmarkedMovies.isNotEmpty()) {
                emit(CallResult.Success(listBookmarkedMovies))
            } else {
                emit(CallResult.Error("Data is empty"))
            }
        }
    }

    override suspend fun unBookmarkMovie(movie: DbMovieBookmark) {
        dao.deleteMovie(movie)
    }
}