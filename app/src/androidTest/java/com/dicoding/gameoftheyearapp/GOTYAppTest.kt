package com.dicoding.gameoftheyearapp

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.dicoding.gameoftheyearapp.model.GOTY
import com.dicoding.gameoftheyearapp.ui.navigation.Screen
import com.dicoding.gameoftheyearapp.ui.theme.GameOfTheYearAppTheme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GOTYAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            GameOfTheYearAppTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                GOTYApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("GameList").performScrollToIndex(5)
        composeTestRule.onNodeWithText(GOTY.games[5].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailGame.route)
        composeTestRule.onNodeWithText(GOTY.games[5].name).assertIsDisplayed()
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_favoriteAdded_favoriteDelete() {
        composeTestRule.onNodeWithText(GOTY.games[2].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailGame.route)
        composeTestRule.onNodeWithContentDescription("Favorite Button").performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithText(GOTY.games[2].name).assertIsDisplayed()

        composeTestRule.onNodeWithText(GOTY.games[2].name).performClick()
        composeTestRule.onNodeWithContentDescription("Favorite Button").performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithText(GOTY.games[2].name).assertDoesNotExist()

    }

}