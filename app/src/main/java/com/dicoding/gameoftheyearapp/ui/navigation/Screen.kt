package com.dicoding.gameoftheyearapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailGame : Screen("home/{gameId}") {
        fun createRoute(gameId: Long) = "home/$gameId"
    }
}
