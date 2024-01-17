package com.raka.movies.data.model

import com.raka.movies.data.db.DbMovieBookmark

data class MovieItemCompact(
    val id: Int,
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
        fun toMovieItemCompact(movie: DbMovieBookmark) =
            MovieItemCompact(
                id = movie.movieId,
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
                runtime = movie.runtime
            )
    }
}