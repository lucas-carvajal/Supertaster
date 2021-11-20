package com.carvajal.lucas.supertaster.composables

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.carvajal.lucas.supertaster.data.Recipe
import com.carvajal.lucas.supertaster.data.RecipeImage

@Composable
fun RecipesList(recipesList: State<List<Recipe>?>, recipesImageList: State<List<RecipeImage>?>) {
    Text(text = "Listing all recipes :P")
}