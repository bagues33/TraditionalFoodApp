package com.example.traditionalfoodapp.ui.screen.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.traditionalfoodapp.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.traditionalfoodapp.di.Injection
import com.example.traditionalfoodapp.model.OrderFood
import com.example.traditionalfoodapp.ui.ViewModelFactory
import com.example.traditionalfoodapp.ui.common.UiState
import com.example.traditionalfoodapp.ui.components.EmptyContent
import com.example.traditionalfoodapp.ui.components.FoodItem
import com.example.traditionalfoodapp.ui.components.SearchView

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllFoods()
            }
            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::search,
                    orderFood = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    orderFood: List<OrderFood>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    Column {
        SearchView(
            query = query,
            onQueryChange = onQueryChange
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.testTag("RewardList")
        ) {
            if (orderFood.isNotEmpty()) {
                items(orderFood) { data ->
                    FoodItem(
                        image = data.food.image,
                        title = data.food.title,
                        price = data.food.price,
                        modifier = Modifier.clickable {
                            navigateToDetail(data.food.id)
                        }
                    )
                }
            } else {
                items(2) { data ->
                    EmptyContent(
                        contentText = stringResource(R.string.empty_data),
                        modifier = Modifier
                            .testTag("empty_data")
                    )
                }
            }
        }
    }

}