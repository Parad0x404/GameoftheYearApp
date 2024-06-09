package com.dicoding.gameoftheyearapp.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.dicoding.gameoftheyearapp.model.Game
import com.dicoding.gameoftheyearapp.model.GameList
import com.dicoding.gameoftheyearapp.ui.theme.GameOfTheYearAppTheme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailScreenKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeGameList = GameList(
        game = Game(
            2,
            "Grand Theft Auto V",
            "Three very different criminals team up for a series of heists and walk into some of the most thrilling experiences in the corrupt city of Los Santos.",
            "https://i.pinimg.com/736x/7b/34/81/7b34815378a30fb718bcdb97b0a1d178.jpg"
        ),
        count = 0
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            GameOfTheYearAppTheme {
                DetailContent(
                    fakeGameList.game.photoUrl,
                    fakeGameList.game.name,
                    fakeGameList.game.desc,
                    fakeGameList.count,
                    onAddToFavorite = {}
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDisplayed() {
        composeTestRule.onNodeWithText(fakeGameList.game.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeGameList.game.desc).assertIsDisplayed()
    }

}