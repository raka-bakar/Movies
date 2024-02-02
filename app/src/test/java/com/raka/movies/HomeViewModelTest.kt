package com.raka.movies

import com.raka.movies.domain.usecase.bookmark.BookmarkMovieUseCase
import com.raka.movies.domain.usecase.bookmark.GetBookmarkedMovies
import com.raka.movies.domain.usecase.bookmark.UnbookmarkMovieUseCase
import com.raka.movies.domain.usecase.movies.GetStaffPickListUseCase
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.ui.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify

class HomeViewModelTest {

    private val getStaffPickListUseCase = Mockito.mock(GetStaffPickListUseCase::class.java)
    private val bookmarkMovieUseCase = Mockito.mock(BookmarkMovieUseCase::class.java)
    private val unbookmarkMovieUseCase = Mockito.mock(UnbookmarkMovieUseCase::class.java)
    private val getBookmarkedMoviesUseCase = Mockito.mock(GetBookmarkedMovies::class.java)

    private lateinit var sut: HomeViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        sut = HomeViewModel(
            getStaffPickListUseCase = getStaffPickListUseCase,
            bookmarkMovieUseCase = bookmarkMovieUseCase,
            dispatcherIo = UnconfinedTestDispatcher(),
            unbookmarkMovieUseCase = unbookmarkMovieUseCase,
            getBookmarkedMovies = getBookmarkedMoviesUseCase
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
        verify(unbookmarkMovieUseCase).unBookmarkMovie(movie)
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
        verify(bookmarkMovieUseCase).bookmarkMovie(movie)
    }
}