package com.carvajal.lucas.supertaster.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.carvajal.lucas.supertaster.R

sealed class BottomBarScreen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Dashboard : BottomBarScreen("dashboard", R.string.dashboard, Icons.Default.Home)
    object Cookbooks : BottomBarScreen("cookbooks", R.string.cookbooks, Icons.Default.Book)
    object Add : BottomBarScreen("add", R.string.add, Icons.Default.Add)
    object WeeklySchedule : BottomBarScreen("weekly_schedule", R.string.weeklySchedule, Icons.Default.Event)
    object ShoppingList : BottomBarScreen("shopping_list", R.string.shoppingList, Icons.Default.ShoppingCart)
}

