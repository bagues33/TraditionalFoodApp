package com.example.traditionalfoodapp.di

import com.example.traditionalfoodapp.data.FoodRepository

object Injection {
    fun provideRepository(): FoodRepository {
        return FoodRepository.getInstance()
    }
}