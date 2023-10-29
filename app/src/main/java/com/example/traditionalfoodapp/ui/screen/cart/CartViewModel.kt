package com.example.traditionalfoodapp.ui.screen.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traditionalfoodapp.data.FoodRepository
import com.example.traditionalfoodapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: FoodRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderFoods() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderFoods()
                .collect { orderFoods ->
                    Log.d("FoodDebug2", "Order Food: ${orderFoods}")
                    val totalRequiredPoint =
                        orderFoods.sumOf { it.food.price * it.count }
                    _uiState.value = UiState.Success(CartState(orderFoods, totalRequiredPoint))
                }
        }
    }

    fun updateOrderFood(foodId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderFood(foodId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderFoods()
                    }
                }
        }
    }
}