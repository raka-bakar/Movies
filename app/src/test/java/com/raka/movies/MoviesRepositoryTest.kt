package com.raka.movies

import com.movies.DataProvider
import com.movies.data.CallResult
import com.movies.data.db.DbMovieBookmark
import com.movies.data.model.MoviesItem
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.repository.MoviesRepository
import com.raka.movies.repository.MoviesRepositoryImpl
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify

class MoviesRepositoryTest {
    private val dataProvider = Mockito.mock(DataProvider::class.java)
    private lateinit var sut: MoviesRepository

    @Before
    fun setup() {
        sut = MoviesRepositoryImpl(
            dataProvider
        )
    }

    @Test
    fun `add the correct data of a new bookmarked movie to DB`() =
        runBlocking {
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
            val dbMovie = DbMovieBookmark(
                movieId = 1,
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
            sut.addBookmark(movie)
            verify(dataProvider).addBookmarkedMovie(dbMovie)
        }

    @Test
    fun `remove the correct data of bookmarked movie from DB`() =
        runBlocking {
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
            val dbMovie = DbMovieBookmark(
                movieId = 1,
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
            sut.unBookmarkMovie(movie)
            verify(dataProvider).deleteBookmarkedMovie(dbMovie)
        }

    @Test
    fun `get the correct movie data from asset file`() =
        runBlocking {
            val dbMovie = DbMovieBookmark(
                movieId = 1,
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
            val data = listOf(MoviesItem(id = 1, title = "title"))
            Mockito.`when`(
                dataProvider.getMovies()
            ).thenReturn(data)

            Mockito.`when`(
                dataProvider.getBookmarkedMovies()
            ).thenReturn(flow { emit(CallResult.Success(listOf(dbMovie))) })

            val result = sut.getMoviesList("")
            result.collect {
                Assert.assertEquals(1, it.data?.get(0)?.id)
                Assert.assertEquals("title", it.data?.get(0)?.title)
            }
        }

    @Test
    fun `data is null when call result is error`() =
        runBlocking {
            Mockito.`when`(
                dataProvider.getMovies()
            ).thenReturn(listOf())

            val result = sut.getMoviesList("")
            result.collect {
                Assert.assertEquals(null, it.data)
            }
        }
}