package com.raka.movies.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raka.movies.data.model.MovieItemCompact
import com.raka.movies.data.model.MoviesItem
import com.raka.movies.data.model.StaffPicksItem
import java.text.DecimalFormat

fun List<MoviesItem>.toCompactList(): List<MovieItemCompact> {
    return this.map {
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

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)

fun List<StaffPicksItem>.toCompactStaffList(): List<MovieItemCompact> {
    return this.map {
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