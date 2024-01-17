package com.raka.movies.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raka.movies.data.model.MoviesItem
import com.raka.movies.data.model.StaffPicksItem
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
}

class DataSourceImpl @Inject constructor(private val context: Context) : DataSource {
    override fun loadMoviesFromAssetFile(): List<MoviesItem> {
        var jsonString = ""
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
        var jsonString = ""
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
}