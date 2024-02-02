package com.raka.movies.domain.usecase.bookmark

import com.raka.movies.model.MovieItemCompact
import com.raka.movies.repository.MoviesRepository
import javax.inject.Inject

/**
 * UseCase for adding a new bookmarked movie
 */
interface BookmarkMovieUseCase {
    /**
     * adding movie as a new bookmarked movie
     * @param movie item of type MovieItemCompact
     */
    suspend fun bookmarkMovie(movie: MovieItemCompact)
}

class BookmarkMovieUseCaseImpl @Inject constructor(private val movieRepository: MoviesRepository) :
    BookmarkMovieUseCase {
    override suspend fun bookmarkMovie(movie: MovieItemCompact) {
        movieRepository.addBookmark(movie)
    }
}