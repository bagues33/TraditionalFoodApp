package com.example.traditionalfoodapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.traditionalfoodapp.data.FoodRepository
import com.example.traditionalfoodapp.ui.screen.cart.CartViewModel
import com.example.traditionalfoodapp.ui.screen.detail.DetailFoodViewModel
import com.example.traditionalfoodapp.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: FoodRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailFoodViewModel::class.java)) {
            return DetailFoodViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}