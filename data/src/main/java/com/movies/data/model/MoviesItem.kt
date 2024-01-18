package com.movies.data.model

import com.google.gson.annotations.SerializedName

data class CastItem(

    @field:SerializedName("character")
    val character: String? = null,

    @field:SerializedName("pictureUrl")
    val pictureUrl: String? = null,

    @field:SerializedName("name")
    val name: String? = null
)

data class MoviesItem(

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("releaseDate")
    val releaseDate: String? = null,

    @field:SerializedName("director")
    val director: Director? = null,

    @field:SerializedName("rating")
    val rating: Float? = null,

    @field:SerializedName("runtime")
    val runtime: Int? = null,

    @field:SerializedName("language")
    val language: String? = null,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("cast")
    val cast: List<CastItem?>? = null,

    @field:SerializedName("revenue")
    val revenue: Int? = null,

    @field:SerializedName("posterUrl")
    val posterUrl: String? = null,

    @field:SerializedName("reviews")
    val reviews: Int? = null,

    @field:SerializedName("genres")
    val genres: List<String?>? = null,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("budget")
    val budget: Int? = null
)

data class Director(

    @field:SerializedName("pictureUrl")
    val pictureUrl: String? = null,

    @field:SerializedName("name")
    val name: String? = null
)