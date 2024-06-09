package com.dicoding.gameoftheyearapp.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.gameoftheyearapp.data.GameRepository
import com.dicoding.gameoftheyearapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: GameRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FavoriteState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavoriteState>>
        get() = _uiState

    fun getAddedGameLists() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedGameLists()
                .collect { gameList ->
                    _uiState.value = UiState.Success(FavoriteState(gameList))
                }
        }
    }

}