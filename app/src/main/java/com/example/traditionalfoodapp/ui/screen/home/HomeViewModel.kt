package com.example.traditionalfoodapp.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traditionalfoodapp.data.FoodRepository
import com.example.traditionalfoodapp.model.OrderFood
import com.example.traditionalfoodapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: FoodRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderFood>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderFood>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchFood(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun getAllFoods() {
        viewModelScope.launch {
            repository.getAllFoods()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderFoods ->
                    _uiState.value = UiState.Success(orderFoods)
                }
        }
    }
}