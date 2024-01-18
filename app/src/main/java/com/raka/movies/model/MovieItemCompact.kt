package com.raka.movies.model

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
)