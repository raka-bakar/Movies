package com.raka.movies.ui.navigation

import androidx.lifecycle.SavedStateHandle

/**
 * Represents an Argument saved and passed in SavedStateHandle
 */
class SavedArgument<T>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    private val initialValue: T
) {
    val flow = savedStateHandle.getStateFlow(key, initialValue)
    fun get() = savedStateHandle.get<T>(key) ?: initialValue
    fun set(newValue: T) {
        savedStateHandle[key] = newValue
    }
}