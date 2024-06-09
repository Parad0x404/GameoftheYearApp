package com.dicoding.gameoftheyearapp.ui.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.gameoftheyearapp.di.Injection
import com.dicoding.gameoftheyearapp.ui.ViewModelFactory
import com.dicoding.gameoftheyearapp.ui.common.UiState
import com.dicoding.gameoftheyearapp.ui.theme.GameOfTheYearAppTheme

@Composable
fun DetailScreen(
    gameId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToFavorite: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getGameById(gameId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.game.photoUrl,
                    data.game.name,
                    data.game.desc,
                    count = data.count,
                    onAddToFavorite = { count ->
                        viewModel.addToFavorite(data.game, count)
                        navigateToFavorite()
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    image: String,
    title: String,
    desc:  String,
    count: Int,
    onAddToFavorite: (count: Int) -> Unit,
) {
    var favCount by rememberSaveable { mutableStateOf(count) }

    Column {
        AsyncImage(
            model = image, contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .size(450.dp)

        )
        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,

                ),
        )
        Text(
            text = desc,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 15.sp,
            ),
        )
    }

    Box(modifier = Modifier.fillMaxSize().padding(22.dp), contentAlignment = Alignment.BottomEnd) {
        FavoriteFAB(
            modifier = Modifier,
            onClick = {
                if (favCount > 0) {
                    onAddToFavorite(favCount - 1)
                } else {
                    onAddToFavorite(favCount + 1)
                }
            },
            favorited = favCount > 0,
        )
    }
}

@Composable
fun FavoriteFAB(
    modifier: Modifier,
    onClick: () -> Unit,
    favorited: Boolean,
) {
    FloatingActionButton(
        modifier = Modifier,
        onClick = onClick,
    ) {
        if (favorited) {
            Icon(Icons.Filled.Favorite, "Favorite Button")
        } else {
            Icon(Icons.Filled.FavoriteBorder, "Favorite Button")
        }
    }
}