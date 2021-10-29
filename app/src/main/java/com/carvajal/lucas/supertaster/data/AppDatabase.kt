package com.carvajal.lucas.supertaster.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class, RecipeImage::class, RecipeIngredient::class, RecipeStep::class],
            version = 1, exportSchema = false
    )
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun recipeImageDao(): RecipeImageDao
    abstract fun recipeIngredientDao(): RecipeIngredientDao
    abstract fun recipeStepDao(): RecipeStepDao
}