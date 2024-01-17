package com.raka.movies.data

sealed class CallResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Initial<T> : CallResult<T>()
    class Success<T>(data: T) : CallResult<T>(data)
    class Error<T>(message: String, data: T? = null) : CallResult<T>(data, message)
}