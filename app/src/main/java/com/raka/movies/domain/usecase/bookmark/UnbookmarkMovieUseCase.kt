package com.raka.movies.domain.usecase.bookmark

import com.raka.movies.model.MovieItemCompact
import com.raka.movies.repository.MoviesRepository
import javax.inject.Inject

/**
 * UseCase for removing a movie from bookmarked movie list
 */
interface UnbookmarkMovieUseCase {
    /**
     * adding movie as a new bookmarked movie
     * @param movie item of type MovieItemCompact
     */
    suspend fun unBookmarkMovie(movie: MovieItemCompact)
}

class UnbookmarkMovieUseCaseImpl @Inject constructor(
    private val movieRepository: MoviesRepository
) :
    UnbookmarkMovieUseCase {
    override suspend fun unBookmarkMovie(movie: MovieItemCompact) {
        movieRepository.unBookmarkMovie(movie)
    }
}