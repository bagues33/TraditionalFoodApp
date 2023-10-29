package com.example.traditionalfoodapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object About : Screen("about")
    object DetailFood : Screen("home/{foodId}") {
        fun createRoute(foodId: Long) = "home/$foodId"
    }
}