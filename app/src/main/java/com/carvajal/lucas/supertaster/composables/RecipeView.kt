package com.carvajal.lucas.supertaster.composables

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.carvajal.lucas.supertaster.R
import com.carvajal.lucas.supertaster.composables.utils.AddToCookbook
import com.carvajal.lucas.supertaster.data.Recipe
import com.carvajal.lucas.supertaster.data.RecipeImage
import com.carvajal.lucas.supertaster.data.RecipeIngredient
import com.carvajal.lucas.supertaster.data.RecipeStep
import com.carvajal.lucas.supertaster.util.getTypeOfMeal
import com.carvajal.lucas.supertaster.viewmodels.RecipeViewListViewModel


@Composable
fun RecipeView(viewModel: RecipeViewListViewModel, navController: NavController) {
    val context = LocalContext.current

    val recipe = viewModel.viewRecipe.observeAsState()
    val recipeImages = viewModel.viewRecipeImages.observeAsState()
    val recipeIngredients = viewModel.viewRecipeIngredients.observeAsState()
    val recipeSteps = viewModel.viewRecipeSteps.observeAsState()

    val scrollState = rememberScrollState()

    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value && recipe.value != null) {
        AddToCookbook(viewModel, openDialog, recipe.value!!, context)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(modifier = Modifier
            .verticalScroll(scrollState)
        ) {
            TopRow(heading = recipe.value?.title ?: stringResource(R.string.error), icon = Icons.Default.Edit) {
                navController.navigate("edit_recipe/${recipe.value?.id}")
            }
            PhotoSlideshow(recipeImages as State<List<RecipeImage>>)
            Divider(modifier = Modifier.padding(0.dp, 10.dp))
            ActionButtonsRow(
                addAction = {
                    openDialog.value = true
                }
            )
            Divider(modifier = Modifier.padding(0.dp, 10.dp))
            TypeAndCuisineRow(recipe)
            Divider(modifier = Modifier.padding(0.dp, 10.dp))
            TimeAndServingsRow(recipe)
            Divider(modifier = Modifier.padding(0.dp, 10.dp))
            IngredientsList(recipeIngredients)
            Divider(modifier = Modifier.padding(0.dp, 10.dp))
            StepsList(recipeSteps)
        }
    }
}

@Composable
fun PhotoSlideshow(photos: State<List<RecipeImage>?>) {
    val scrollState = rememberScrollState()

    if (!photos.value.isNullOrEmpty()) {
        Row(
            modifier = Modifier.horizontalScroll(scrollState)
        ) {
            photos.value?.forEach { photo ->
                Image(
                    bitmap = BitmapFactory.decodeFile(photo.location).asImageBitmap(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5.dp))
                        .aspectRatio(1f)
                        .padding(10.dp)
                )
            }

        }
    } else {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .padding(10.dp)
        ){
            Text(
                text = stringResource(R.string.no_image_available),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }


}

@Composable
fun ActionButtonsRow(
    addAction: () -> Unit
) {
    val context = LocalContext.current

    Row {
        Spacer(Modifier.weight(1f))
        IconButton(
            modifier = Modifier.border(2.dp, MaterialTheme.colors.onSurface, shape = CircleShape),
            onClick = {
                addAction()
            }
        ) {
            Icon(Icons.Default.Add, contentDescription = "", tint = MaterialTheme.colors.onSurface)
        }
        Spacer(Modifier.weight(1f))
        IconButton(
            modifier = Modifier.border(2.dp, MaterialTheme.colors.onSurface, shape = CircleShape),
            onClick = {
                //TODO add to weekly schedule
                Toast.makeText(context, "Add to weekly schedule", Toast.LENGTH_SHORT).show()
            }
        ) {
            Icon(Icons.Default.Event, contentDescription = "", tint = MaterialTheme.colors.onSurface)
        }
        Spacer(Modifier.weight(1f))
        IconButton(
            modifier = Modifier.border(2.dp, MaterialTheme.colors.onSurface, shape = CircleShape),
            onClick = {
                //TODO add ingredients to shopping list
                Toast.makeText(context, "Add ingredients to Shopping List", Toast.LENGTH_SHORT).show()
            }
        ) {
            Icon(Icons.Default.ShoppingCart, contentDescription = "", tint = MaterialTheme.colors.onSurface)
        }
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun TypeAndCuisineRow(recipe: State<Recipe?>) {
    Row {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = getTypeOfMeal(recipe.value?.typeOfMealIndex ?: -1) ?: "",
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "-",
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = recipe.value?.cuisine ?: "",
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun TimeAndServingsRow(recipe: State<Recipe?>) {
    Row {
        Spacer(modifier = Modifier.weight(1f))
        NumWithDesc(
            number = recipe.value?.servings ?: 0,
            description = stringResource(R.string.servings)
        )
        Spacer(modifier = Modifier.weight(1f))
        NumWithDesc(
            number = recipe.value?.prepTime ?: 0,
            description = stringResource(R.string.prep_time)
        )
        Spacer(modifier = Modifier.weight(1f))
        NumWithDesc(
            number = recipe.value?.cookTime ?: 0,
            description = stringResource(R.string.cook_time)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun NumWithDesc(number: Int, description: String) {
    Column {
        Text(
            text = description,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = number.toString(),
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}



@Composable
fun IngredientsList(ingredients: State<List<RecipeIngredient>?>) {
    Column {
        if (!ingredients.value.isNullOrEmpty()) {
            ingredients.value?.forEach { ingredient ->
                Row (
                    modifier = Modifier.padding(15.dp , 0.dp)
                ) {
                    Text(text = ingredient.ingredient)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = ingredient.amount)
                }
            }
        } else {
            Text(
                text = stringResource(R.string.no_ingredients_available),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun StepsList(steps: State<List<RecipeStep>?>) {
    Column {
        if (!steps.value.isNullOrEmpty()) {
            val sortedSteps = steps.value?.sortedBy { it.sequence }
            sortedSteps?.forEach { step ->
                Row(
                    modifier = Modifier.padding(15.dp , 5.dp)
                ) {
                    Text(
                        text = step.sequence.toString(),
                        fontSize = MaterialTheme.typography.h5.fontSize,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 30.dp)
                    )
                    Column {
                        Text(
                            text = step.description,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = step.extraNotes,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        } else {
            Text(
                text = stringResource(R.string.no_steps_available),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}



