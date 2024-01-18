package com.movies.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movies.data.db.Constants.MOVIE_BOOKMARK_TABLE
import kotlinx.coroutines.flow.Flow

/**
 * Room Dao interface , provides methods for persisting Bookmark Movie
 */
@Dao
interface MovieBookmarkDao {
    /**
     * get a list of bookmarkedMovie from the database
     * @return List<DbMovieBookmark>
     */
    @Query("SELECT * FROM $MOVIE_BOOKMARK_TABLE")
    fun getBookmarkedMovies(): List<DbMovieBookmark>

    /**
     * get a bookmarkedMovie from the database using movieId
     * @param movieId id of the movie
     * @return Flow<DbMovieBookmark>
     */
    @Query("SELECT * FROM $MOVIE_BOOKMARK_TABLE WHERE movieId = :movieId")
    fun getBookmarkedMovie(movieId: Int): Flow<DbMovieBookmark>

    /**
     * add a new movie to the bookmarked database
     * @param item of DbMovieBookmark
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bookmarkMovie(item: DbMovieBookmark)

    /**
     * delete an existing bookmarked movie in the database
     * @param item of DbMovieBookmark
     */
    @Delete
    suspend fun deleteMovie(item: DbMovieBookmark)
}