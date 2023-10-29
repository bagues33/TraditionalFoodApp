package com.example.traditionalfoodapp

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.traditionalfoodapp.model.FakeFoodDataSource
import com.example.traditionalfoodapp.ui.navigation.Screen
import com.example.traditionalfoodapp.ui.theme.TraditionalFoodAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JetTraditionalAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController
    @Before
    fun setUp() {
        composeTestRule.setContent {
            TraditionalFoodAppTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                JetTraditionalApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("FoodList").performScrollToIndex(10)
        composeTestRule.onNodeWithText(FakeFoodDataSource.dummyFoods[10].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailFood.route)
        composeTestRule.onNodeWithText(FakeFoodDataSource.dummyFoods[10].title).assertIsDisplayed()
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_cart).performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.About.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesBack() {
        composeTestRule.onNodeWithTag("FoodList").performScrollToIndex(10)
        composeTestRule.onNodeWithText(FakeFoodDataSource.dummyFoods[10].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailFood.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_checkout_rightBackStack() {
        composeTestRule.onNodeWithText(FakeFoodDataSource.dummyFoods[4].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailFood.route)
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }
}

