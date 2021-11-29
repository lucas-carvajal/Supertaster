package com.carvajal.lucas.supertaster.composables

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.carvajal.lucas.supertaster.data.Recipe
import com.carvajal.lucas.supertaster.data.RecipeImage
import com.carvajal.lucas.supertaster.viewmodels.RecipesListViewModel

@Composable
fun RecipesList(
    recipesList: State<List<Recipe>?>,
    recipesImageList: State<List<RecipeImage>?>,
    viewModel: RecipesListViewModel
) {
    Column(modifier = Modifier.padding(bottom = 10.dp)) {
        recipesList.value?.forEach { recipe ->
            val recipeImages = recipesImageList.value?.filter { it.recipeId == recipe.id }
            RecipeListItem(recipe, recipeImages)
        }
    }
}

@Composable
fun RecipeListItem(recipe: Recipe, recipeImages: List<RecipeImage>?) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp),
        elevation = mainCardElevation
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = recipe.title,
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Row {
                if (!recipeImages.isNullOrEmpty()) {
                    Image(
                        bitmap = BitmapFactory.decodeFile(recipeImages!!.first().location).asImageBitmap(),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .width(100.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(5.dp))
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .width(50.dp)
                            .aspectRatio(1f)
                    ) {
                        Text(
                            text = "No image available",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                Column {
                    Text(
                        text = (recipe.prepTime + recipe.cookTime).toString() + " minutes",
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxWidth()
                    )
                    ActionButtonsRow {
                        //TODO
                    }
                }
            }
        }
    }
}
