package com.example.traditionalfoodapp.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.traditionalfoodapp.R
import com.example.traditionalfoodapp.model.Food
import com.example.traditionalfoodapp.model.OrderFood
import com.example.traditionalfoodapp.onNodeWithStringId
import com.example.traditionalfoodapp.ui.theme.TraditionalFoodAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailContentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeOrderFood = OrderFood(
        food = Food(4, R.drawable.risoles, "Risoles", 1500, "Risoles, atau biasa disebut risol, adalah pastri berisi daging, biasanya daging cincang, dan sayuran yang dibungkus dadar, dan digoreng setelah dilapisi tepung panir dan kocokan telur ayam. Hidangan ini juga dapat dipanggang di dalam oven, dan disajikan sebagai hors-d'oeuvre atau entrée ringan"),
        count = 0
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            TraditionalFoodAppTheme() {
                DetailContent(
                    fakeOrderFood.food.image,
                    fakeOrderFood.food.title,
                    fakeOrderFood.food.price,
                    fakeOrderFood.food.description,
                    fakeOrderFood.count,
                    onBackClick = {},
                    onAddToCart = {}
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDisplayed() {
        composeTestRule.onNodeWithText(fakeOrderFood.food.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.price,
                fakeOrderFood.food.price
            )
        ).assertIsDisplayed()
    }

    @Test
    fun increaseProduct_buttonEnabled() {
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsEnabled()
    }

    @Test
    fun increaseProduct_correctCounter() {
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick().performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("2"))

    }

    @Test
    fun decreaseProduct_stillZero() {
        composeTestRule.onNodeWithStringId(R.string.minus_symbol).performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("0"))
    }
}