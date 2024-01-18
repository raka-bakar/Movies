package com.movies.data

/**
 * class representing formatted data call results, whether from database or local file.
 * instances of this class cannot be modified as they will not be able to notify listeners
 * @param data actual data
 * @param message call message
 */
sealed class CallResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Initial<T> : CallResult<T>()
    class Success<T>(data: T) : CallResult<T>(data)
    class Error<T>(message: String, data: T? = null) : CallResult<T>(data, message)
}