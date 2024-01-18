package com.movies.data.di

import android.content.Context
import androidx.room.Room
import com.movies.data.db.Constants.DB
import com.movies.data.db.MovieBookmarkDao
import com.movies.data.db.MovieDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger providers for Database components
 */
@Module
internal class DatabaseModule {
    /**
     * provides room database instance
     */
    @Singleton
    @Provides
    fun providesRoomDatabase(context: Context): MovieDb {
        return Room.databaseBuilder(
            context,
            MovieDb::class.java,
            DB
        ).build()
    }

    /**
     * provides room MovieBookmarkDao
     */
    @Provides
    @Singleton
    fun provideMovieBookmarkDao(db: MovieDb): MovieBookmarkDao {
        return db.movieBookmarkDao()
    }
}