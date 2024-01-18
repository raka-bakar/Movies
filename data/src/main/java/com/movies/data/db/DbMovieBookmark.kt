package com.movies.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.movies.data.db.Constants.MOVIE_BOOKMARK_TABLE

/**
 * Model Representing a bookmark of movie
 */
@Entity(tableName = MOVIE_BOOKMARK_TABLE)
data class DbMovieBookmark(
    @PrimaryKey
    val movieId: Int,
    val title: String,
    val genres: List<String?>?,
    val posterUrl: String? = null,
    val overview: String? = null,
    val revenue: Int? = null,
    val rating: Float? = null,
    val language: String? = null,
    val budget: Int? = null,
    val reviews: Int? = null,
    var isBookmarked: Boolean = false,
    val releaseDate: String? = null,
    val runtime: Int? = null
)