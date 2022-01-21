package com.carvajal.lucas.supertaster.util

val typeOfMealMap = mapOf(
    0 to "-",
    1 to "Breakfast",
    2 to "Brunch",
    3 to "Lunch",
    4 to "Tea",
    5 to "Dinner",
    6 to "Snack",
    7 to "Drink",
    8 to "Other"
)

fun getTypeOfMeal(index: Int): String? {
    return typeOfMealMap[index]
}
