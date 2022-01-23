package com.carvajal.lucas.supertaster.composables

import android.widget.Toast
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Transformations
import androidx.navigation.NavController
import com.carvajal.lucas.supertaster.R
import com.carvajal.lucas.supertaster.viewmodels.CookbookViewModel
import com.carvajal.lucas.supertaster.viewmodels.DashboardViewModel
import com.carvajal.lucas.supertaster.viewmodels.RecipesListViewModel

@Composable
fun AllRecipesListHolder(viewModel: RecipesListViewModel, navController: NavController) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column {
            Text(
                text = viewModel.listTitle ?: stringResource(R.string.all_recipes),
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold
            )
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                val recipesList = viewModel.listRecipes.observeAsState()
                val listRecipeImages = viewModel.listRecipeImages.observeAsState()

                RecipesList(recipesList, listRecipeImages, viewModel, navController)
            }
        }

    }
}


@Composable
fun CookbookRecipesListHolder(viewModel: CookbookViewModel, navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val cookbook = viewModel.getCurrentCoookbook().observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column {
            TopRow(heading = cookbook.value?.name ?: stringResource(R.string.error), icon = Icons.Default.Edit) {
                //TODO edit the list
                Toast.makeText(context, "Edit the List", Toast.LENGTH_SHORT).show()
            }
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                val listRecipeImages = viewModel.listRecipeImages.observeAsState()
                val recipesInCookbook = viewModel.recipesInCookbook.observeAsState()

                val recipesList = Transformations.map(viewModel.listRecipes) {
                    if (!recipesInCookbook.value.isNullOrEmpty()) {
                        it.filter { recipe ->
                            println("T E S T : : : ${recipe.title} : : ${recipesInCookbook.value!!.contains(recipe.id)}")
                            recipesInCookbook.value!!.contains(recipe.id)
                        }
                    } else {
                        listOf()
                    }
                }.observeAsState()

                RecipesList(recipesList, listRecipeImages, viewModel, navController)
            }
        }
    }
}