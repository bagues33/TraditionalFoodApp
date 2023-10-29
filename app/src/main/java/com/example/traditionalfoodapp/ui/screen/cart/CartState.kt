package com.example.traditionalfoodapp.ui.screen.cart

import com.example.traditionalfoodapp.model.OrderFood

data class CartState(
    val orderFood: List<OrderFood>,
    val price: Int
)