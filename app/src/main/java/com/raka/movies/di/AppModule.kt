package com.raka.movies.di

import android.content.Context
import androidx.room.Room
import com.raka.movies.data.DataSource
import com.raka.movies.data.DataSourceImpl
import com.raka.movies.data.db.Constants.DB
import com.raka.movies.data.db.MovieDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideDataSource(@ApplicationContext context: Context): DataSource =
        DataSourceImpl(context)

    @Singleton
    @Provides
    fun provideMovieDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MovieDb::class.java, DB).build()

    @Singleton
    @Provides
    fun provideMovieBookmarDao(movieDb: MovieDb) = movieDb.movieBookmarkDao()
}