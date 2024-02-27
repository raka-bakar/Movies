package com.raka.movies.util

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.ComposeContentTestRule

@OptIn(ExperimentalTestApi::class)
object TestUtil {
    fun String.readFile(c: Any) : String =
        c.javaClass.classLoader!!.getResource(this).readText()

    fun ComposeContentTestRule.waitUntilExists(
        matcher: SemanticsMatcher,
        timeoutMillis: Long = 1_000L
    ) {
        return this.waitUntilNodeCount(matcher, 1, timeoutMillis)
    }
}