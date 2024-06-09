package com.dicoding.gameoftheyearapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.gameoftheyearapp.data.GameRepository
import com.dicoding.gameoftheyearapp.model.Game
import com.dicoding.gameoftheyearapp.model.GameList
import com.dicoding.gameoftheyearapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: GameRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<GameList>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<GameList>>
        get() = _uiState

    fun getGameById(rewardId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getGameListById(rewardId))
        }
    }

    fun addToFavorite(game: Game, count: Int) {
        viewModelScope.launch {
            repository.updateGameList(game.id, count)
        }
    }
}