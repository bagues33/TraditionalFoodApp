package com.example.traditionalfoodapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traditionalfoodapp.data.FoodRepository
import com.example.traditionalfoodapp.model.Food
import com.example.traditionalfoodapp.model.OrderFood
import com.example.traditionalfoodapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailFoodViewModel(
    private val repository: FoodRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderFood>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderFood>>
        get() = _uiState

    fun getFoodById(foodId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderFoodById(foodId))
        }
    }

    fun addToCart(food: Food, count: Int) {
        viewModelScope.launch {
            repository.updateOrderFood(food.id, count)
        }
    }
}