package com.movies

import android.content.Context
import com.movies.data.CallResult
import com.movies.data.db.DbMovieBookmark
import com.movies.data.di.DaggerAppComponent
import com.movies.data.model.MoviesItem
import com.movies.data.model.StaffPicksItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * This class is the entry point to the module, it's responsible for
 * creating and exposing data calls
 */

class DataProvider(context: Context) {
    @Inject
    internal lateinit var dataSource: DataSource

    init {
        val component = DaggerAppComponent.builder().context(context).build()
        component.inject(this)
    }

    fun getStaffPick(): List<StaffPicksItem> {
        return dataSource.loadStaffPicksFromAssetFile()
    }

    fun getMovies(): List<MoviesItem> {
        return dataSource.loadMoviesFromAssetFile()
    }

    suspend fun addBookmarkedMovie(movie: DbMovieBookmark) {
        dataSource.addBookmark(movie)
    }

    fun getBookmarkedMovies(): Flow<CallResult<List<DbMovieBookmark>>> {
        return dataSource.getBookmarkedMovies()
    }

    suspend fun deleteBookmarkedMovie(movie: DbMovieBookmark) {
        dataSource.unBookmarkMovie(movie)
    }
}