package com.carvajal.lucas.supertaster.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carvajal.lucas.supertaster.viewmodels.AddViewViewModel


@Composable
fun RecipeView(viewModel: AddViewViewModel) {
    val recipe = viewModel.viewRecipe.observeAsState()
    val recipeImages = viewModel.viewRecipeImages.observeAsState()
    val recipeIngredients = viewModel.viewRecipeIngredients.observeAsState()
    val recipeSteps = viewModel.viewRecipeSteps.observeAsState()

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(modifier = Modifier
            .verticalScroll(scrollState)
        ) {
            TopRow(heading = recipe.value?.title ?: "error", icon = Icons.Default.Edit) {
                // TODO navigate to add view to edit the recipe
            }
            //TODO add fields
            Text(text = recipeImages.toString())
            Text(text = recipeIngredients.toString())
            Text(text = recipeSteps.toString())
        }
    }

}