package com.raka.movies.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.raka.movies.utils.fromJson
import timber.log.Timber

class Converter {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return try {
            Gson().fromJson<List<String>>(value)
        } catch (e: Exception) {
            Timber.e(e)
            listOf()
        }
    }
}