package com.carvajal.lucas.supertaster.ui

import androidx.annotation.StringRes
import com.carvajal.lucas.supertaster.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Dashboard : Screen("dashboard", R.string.dashboard)
    object Cookbooks : Screen("cookbooks", R.string.cookbooks)
    object Add : Screen("add", R.string.add)
    object WeeklySchedule : Screen("weekly_schedule", R.string.weeklySchedule)
    object ShoppingList : Screen("shopping_list", R.string.shoppingList)
}
