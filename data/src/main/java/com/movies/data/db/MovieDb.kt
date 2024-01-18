package com.movies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DbMovieBookmark::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class MovieDb : RoomDatabase() {
    abstract fun movieBookmarkDao(): MovieBookmarkDao
}