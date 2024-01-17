package com.raka.movies.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raka.movies.data.db.Constants.MOVIE_BOOKMARK_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieBookmarkDao {
    @Query("SELECT * FROM $MOVIE_BOOKMARK_TABLE")
    fun getBookmarkedMovies(): List<DbMovieBookmark>

    @Query("SELECT * FROM $MOVIE_BOOKMARK_TABLE WHERE movieId = :movieId")
    fun getBookmarkedMovie(movieId: Int): Flow<DbMovieBookmark>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bookmarkMovie(item: DbMovieBookmark)

    @Delete
    suspend fun deleteMovie(item: DbMovieBookmark)
}