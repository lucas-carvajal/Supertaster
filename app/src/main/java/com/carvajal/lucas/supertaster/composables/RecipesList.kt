package com.carvajal.lucas.supertaster.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.carvajal.lucas.supertaster.data.Recipe
import com.carvajal.lucas.supertaster.data.RecipeImage
import com.carvajal.lucas.supertaster.viewmodels.RecipesListViewModel

@Composable
fun RecipesList(
    recipesList: State<List<Recipe>?>,
    recipesImageList: State<List<RecipeImage>?>,
    viewModel: RecipesListViewModel
) {
    Column {
        recipesList.value?.forEach { recipe ->
            RecipeListItem(recipe)
        }
    }
}

@Composable
fun RecipeListItem(recipe: Recipe) {
    Text(text = recipe.title)
}