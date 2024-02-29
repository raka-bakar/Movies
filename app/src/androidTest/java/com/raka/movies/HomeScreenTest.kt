package com.raka.movies

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.raka.movies.ui.RootActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<RootActivity>()

    @Test
    fun testIfFirstItemIsClickable() {
        composeRule.onNodeWithTag("image1").assertHasClickAction()
    }

    @Test
    fun testAllComponentsInHomeScreen() {
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("username").assertIsDisplayed()
        composeRule.onNodeWithTag("greeting").assertIsDisplayed()
        composeRule.onNodeWithTag("searchButton").assertIsDisplayed().assertHasClickAction()

        composeRule.onNodeWithTag("imageProfile").assertIsDisplayed()

        composeRule.onNodeWithTag("staffList").onChildAt(0).assertIsDisplayed()
        composeRule.onNodeWithTag("favoriteList").onChildAt(0).assertIsDisplayed()
    }

    @Test
    fun testOpenDetailScreenFromHomeScreen() {
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("favoriteList").onChildAt(0).performClick()

        composeRule.waitForIdle()
        composeRule.onNodeWithTag("detailTitle").assertIsDisplayed()
    }

    @Test
    fun testOpenSearchScreenFromHomeScreen() {
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("searchButton").performClick()

        composeRule.waitForIdle()
        composeRule.onNodeWithTag("searchTopAppBar").assertIsDisplayed()

//        Espresso.pressBack()
    }
}