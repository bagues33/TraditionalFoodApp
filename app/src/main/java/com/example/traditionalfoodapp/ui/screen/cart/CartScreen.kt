package com.example.traditionalfoodapp.ui.screen.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.traditionalfoodapp.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.traditionalfoodapp.di.Injection
import com.example.traditionalfoodapp.ui.ViewModelFactory
import com.example.traditionalfoodapp.ui.common.UiState
import com.example.traditionalfoodapp.ui.components.CartItem
import com.example.traditionalfoodapp.ui.components.OrderButton

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onOrderButtonClicked: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedOrderFoods()
            }
            is UiState.Success -> {
                CartContent(
                    uiState.data,
                    onProductCountChanged = { foodId, count ->
                        viewModel.updateOrderFood(foodId, count)
                    },
                    onOrderButtonClicked = onOrderButtonClicked
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun CartContent(
    state: CartState,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    onOrderButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shareMessage = stringResource(
        R.string.share_message,
        state.orderFood.count(),
        state.price
    )
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(backgroundColor = MaterialTheme.colors.surface) {
            Text(
                text = stringResource(R.string.menu_cart),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
        OrderButton(
            text = stringResource(R.string.total_order, state.price),
            enabled = state.orderFood.isNotEmpty(),
            onClick = {
                onOrderButtonClicked(shareMessage)
            },
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(state.orderFood, key = { it.food.id }) { item ->
                CartItem(
                    foodId = item.food.id,
                    image = item.food.image,
                    title = item.food.title,
                    price = item.food.price * item.count,
                    count = item.count,
                    onProductCountChanged = onProductCountChanged,
                )
                Divider()
            }
        }
    }
}