package com.carvajal.lucas.supertaster.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carvajal.lucas.supertaster.viewmodels.AddViewViewModel

@Composable
fun RecipeView(viewModel: AddViewViewModel) {
    val recipe = viewModel.recipe

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(modifier = Modifier
            .verticalScroll(scrollState)
        ) {
            TopRow(heading = recipe.value!!.title, icon = Icons.Default.Edit) {
                // TODO navigate to add view to edit the recipe
            }
        }
    }

}