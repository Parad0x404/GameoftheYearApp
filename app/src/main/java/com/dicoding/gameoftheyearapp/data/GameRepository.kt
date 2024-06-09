package com.dicoding.gameoftheyearapp.data

import com.dicoding.gameoftheyearapp.model.GOTY
import com.dicoding.gameoftheyearapp.model.Game
import com.dicoding.gameoftheyearapp.model.GameList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class GameRepository {

    private val gameList = mutableListOf<GameList>()

    init {
        if (gameList.isEmpty()) {
            GOTY.games.forEach {
                gameList.add(GameList(it, 0))
            }
        }
    }

    fun getGames(): List<Game> {
        return GOTY.games
    }

    fun searchGames(query: String): List<Game> {
        return GOTY.games.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun getAllGames(): Flow<List<GameList>> {
        return flowOf(gameList)
    }

    fun getGameListById(gameId: Long): GameList {
        return gameList.first {
            it.game.id == gameId
        }
    }

    fun updateGameList(gameId: Long, newCountValue: Int): Flow<Boolean> {
        val index = gameList.indexOfFirst { it.game.id == gameId }
        val result = if (index >= 0) {
            val games = gameList[index]
            gameList[index] =
                games.copy(game = games.game, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedGameLists(): Flow<List<GameList>> {
        return getAllGames()
            .map { gameLists ->
                gameLists.filter { gameList ->
                    gameList.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: GameRepository? = null

        fun getInstance(): GameRepository =
            instance ?: synchronized(this) {
                GameRepository().apply {
                    instance = this
                }
            }
    }
}