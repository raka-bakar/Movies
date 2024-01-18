package com.raka.movies

import com.raka.movies.domain.usecase.BookmarkUseCase
import com.raka.movies.domain.usecase.MoviesUseCase
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.ui.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify

class SearchViewModelTest {
    private val movieUseCase = Mockito.mock(MoviesUseCase::class.java)
    private val bookmarkUseCase = Mockito.mock(BookmarkUseCase::class.java)

    private lateinit var sut: SearchViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        sut = SearchViewModel(
            moviesUseCase = movieUseCase,
            bookmarkUseCase = bookmarkUseCase,
            dispatcherIo = UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `when isBookmark status is true, unBookmarkMovie() is called`() = runBlocking {
        val movie = MovieItemCompact(
            id = 1,
            title = "title",
            genres = listOf(),
            posterUrl = "url",
            overview = "overview",
            revenue = 0,
            rating = 1.2f,
            language = "en",
            budget = 0,
            reviews = 10,
            isBookmarked = true,
            releaseDate = "releasedate",
            runtime = 10
        )
        sut.onBookmarkClicked(movie)
        verify(bookmarkUseCase).unBookmarkMovie(movie)
    }

    @Test
    fun `when isBookmark status is false, bookmarkMovie() is called`() = runBlocking {
        val movie = MovieItemCompact(
            id = 1,
            title = "title",
            genres = listOf(),
            posterUrl = "url",
            overview = "overview",
            revenue = 0,
            rating = 1.2f,
            language = "en",
            budget = 0,
            reviews = 10,
            isBookmarked = false,
            releaseDate = "releasedate",
            runtime = 10
        )
        sut.onBookmarkClicked(movie)
        verify(bookmarkUseCase).bookmarkMovie(movie)
    }
}