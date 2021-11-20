package com.carvajal.lucas.supertaster.composables

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.carvajal.lucas.supertaster.viewmodels.CookbookViewModel
import com.carvajal.lucas.supertaster.viewmodels.RecipesListViewModel

@Composable
fun AllRecipesListHolder(viewModel: RecipesListViewModel) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column {
            Text(
                text = "All Recipes",
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold
            )
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                val recipesList = viewModel.listRecipes.observeAsState()
                val listRecipeImages = viewModel.listRecipeImages.observeAsState()

                RecipesList(recipesList, listRecipeImages)
            }
        }

    }
}


@Composable
fun CookbookRecipesListHolder(viewModel: CookbookViewModel) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column {
            TopRow(heading = viewModel.getCookbookTitle(), icon = Icons.Default.Edit) {
                //TODO edit the list
                Toast.makeText(context, "Edit the List", Toast.LENGTH_SHORT).show()
            }
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                val recipesList = viewModel.listRecipes.observeAsState()
                val listRecipeImages = viewModel.listRecipeImages.observeAsState()
                val recipesInCookbook = viewModel.recipesInCookbook.observeAsState()

                if (!recipesList.value.isNullOrEmpty()) {
                    recipesList.value?.filter { recipesInCookbook.value!!.contains(it.id) }
                }

                RecipesList(recipesList, listRecipeImages)
            }
        }
    }
}