package com.raka.movies.utils

import com.movies.data.db.DbMovieBookmark
import com.movies.data.model.MoviesItem
import com.movies.data.model.StaffPicksItem
import com.raka.movies.model.MovieItemCompact
import java.text.DecimalFormat

object Utils {
    fun toCompactList(list: List<MoviesItem>): List<MovieItemCompact> {
        return list.map {
            MovieItemCompact(
                id = it.id,
                title = it.title,
                genres = it.genres,
                posterUrl = it.posterUrl,
                overview = it.overview,
                revenue = it.revenue,
                rating = it.rating,
                language = it.language,
                budget = it.budget,
                reviews = it.reviews,
                isBookmarked = false,
                releaseDate = it.releaseDate,
                runtime = it.runtime
            )
        }
    }

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
            releaseDate = movie.releaseDate,
            runtime = movie.runtime
        )

    fun toCompactStaffList(list: List<StaffPicksItem>): List<MovieItemCompact> {
        return list.map {
            MovieItemCompact(
                id = it.id,
                title = it.title,
                genres = it.genres,
                posterUrl = it.posterUrl,
                overview = it.overview,
                revenue = it.revenue,
                rating = it.rating,
                language = it.language,
                budget = it.budget,
                reviews = it.reviews,
                isBookmarked = false,
                releaseDate = it.releaseDate,
                runtime = it.runtime
            )
        }
    }

    fun getEmojiByUnicode(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

    fun currencyFormatter(num: String): String? {
        val m = num.toDouble()
        val formatter = DecimalFormat("###,###,###")
        return formatter.format(m)
    }

    fun formatLanguage(text: String): String {
        return when (text) {
            "en" -> "English"
            "fr" -> "French"
            else -> text
        }
    }
}