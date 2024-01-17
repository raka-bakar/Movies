package com.raka.movies.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raka.movies.data.db.Constants.MOVIE_BOOKMARK_TABLE
import com.raka.movies.data.model.MovieItemCompact

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
) {
    companion object {
        fun toDbMovieBookmark(movie: MovieItemCompact) =
            DbMovieBookmark(
                movieId = movie.id,
                title = movie.title,
                genres = movie.genres,
                posterUrl = movie.posterUrl,
                overview = movie.overview,
                revenue = movie.revenue,
                rating = movie.rating,
                language = movie.language,
                budget = movie.budget,
                reviews = movie.reviews,
                isBookmarked = movie.isBookmarked,
                releaseDate = movie.releaseDate,
                runtime = movie.runtime
            )
    }
}