package com.raka.movies

import com.movies.data.CallResult
import com.raka.movies.domain.usecase.BookmarkUseCase
import com.raka.movies.domain.usecase.MoviesUseCase
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.ui.detail.DetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify

class DetailViewModelTest {
    private val movieUseCase = Mockito.mock(MoviesUseCase::class.java)
    private val bookmarkUseCase = Mockito.mock(BookmarkUseCase::class.java)

    private lateinit var sut: DetailViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        sut = DetailViewModel(
            moviesUseCase = movieUseCase,
            bookmarkUseCase = bookmarkUseCase,
            dispatcherIo = UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `fetch the right data when CallResult is success`() = runBlocking {
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
        val expected = CallResult.Success(movie)
        Mockito.`when`(
            movieUseCase.getMovie(1)
        ).thenReturn(flow {
            emit(CallResult.Success(movie))
        })
        sut.getMovie(1)
        val result = sut._movie.value
        Assert.assertEquals(expected.data?.title, result.data?.title)
    }

    @Test
    fun `data is null when CallResult is Error`() = runBlocking {
        Mockito.`when`(
            movieUseCase.getMovie(1)
        ).thenReturn(flow {
            emit(CallResult.Error("error", null))
        })
        sut.getMovie(1)
        val result = sut._movie.value
        Assert.assertEquals(null, result.data)
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