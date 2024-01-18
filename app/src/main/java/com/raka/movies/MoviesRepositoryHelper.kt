package com.raka.movies

import com.movies.data.db.DbMovieBookmark
import com.movies.data.model.MoviesItem
import com.raka.movies.model.MovieItemCompact

/**
 * a class which has helper functions for MoviesRepository
 */
interface MoviesRepositoryHelper {
    fun List<MoviesItem>.toCompactList(): List<MovieItemCompact>

    fun toDbMovieBookmark(movie: MovieItemCompact): DbMovieBookmark
}

