package com.carvajal.lucas.supertaster.composables

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carvajal.lucas.supertaster.data.Recipe
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme
import com.carvajal.lucas.supertaster.viewmodels.DashboardViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.carvajal.lucas.supertaster.data.RecipeImage
import com.carvajal.lucas.supertaster.ui.ProfileActivity
import com.carvajal.lucas.supertaster.viewmodels.RecipeViewViewModel

val mainCardElevation = 5.dp
val nestedCardElevation = 3.dp


//TODO have option for when recipe has no image

@Composable
fun DashboardScreen(viewModel: DashboardViewModel, navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val openFilterByTimeDialog = remember { mutableStateOf(false) }
    val openFilterByCuisineDialog = remember { mutableStateOf(false) }

    if (openFilterByTimeDialog.value) {
        FilterByTime(viewModel, openFilterByTimeDialog, navController)
    }

    if (openFilterByCuisineDialog.value) {
        FilterByCuisine(viewModel, openFilterByTimeDialog, navController)
    }


    val sampleRecipes = viewModel.sampleRecipes.observeAsState(listOf()).value
    val recipeImages = viewModel.recipeImages.observeAsState(listOf()).value
    val recipeSuggestions = viewModel.getSuggestions().observeAsState(listOf()).value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column (modifier = Modifier
            .verticalScroll(scrollState)
        ) {
            TopRow(heading = viewModel.getGreeting(), icon = Icons.Default.Person) {
                context.startActivity(Intent(context, ProfileActivity::class.java))
            }
            SuggestionsCard(recipeSuggestions, recipeImages, viewModel, navController)
            SearchCard(viewModel, navController, openFilterByTimeDialog, openFilterByCuisineDialog)
            AllRecipesCard(sampleRecipes, recipeImages, viewModel, navController)
            Spacer(Modifier.padding(5.dp))
        }
    }
}


@Composable
fun SuggestionsCard(
    recipeSuggestions: List<Recipe>,
    recipeImages: List<RecipeImage>,
    viewModel: DashboardViewModel,
    navController: NavController
) {
    val scrollState = rememberScrollState()

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp),
        elevation = mainCardElevation
    ){
        Column {
            Text(
                "Suggestions for you",
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp, 5.dp)
            )
            Row (
                modifier = Modifier
                    .horizontalScroll(scrollState)
            ) {
                recipeSuggestions.forEachIndexed { index, recipe ->
                    RecipeCard(
                        recipe = recipe,
                        if (recipeImages.any { it.recipeId == recipe.id }) {
                            BitmapFactory.decodeFile(
                                recipeImages.first { it.recipeId == recipe.id }.location
                            )
                        } else {
                            null
                        }
                    ) {
                        val recipeId = recipeSuggestions[index].id
                        viewModel.setRecipeId(recipeId)
                        navController.navigate("recipe_view_dashboard")
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, recipeImage: Bitmap?, action: () -> Unit) {
    Card (
        modifier = Modifier
            .padding(10.dp)
            .width(140.dp)
            .height(210.dp)
            .clickable { action() },
        elevation = nestedCardElevation,
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
        ) {
            if (recipeImage != null) {
                Image(
                    bitmap = recipeImage.asImageBitmap(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .weight(0.1f)
                )
            } else {
                //TODO no image ?
            }
            Text(
                text = recipe.title,
                modifier = Modifier.padding(start = 5.dp),
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
fun SearchCard(
    viewModel: DashboardViewModel,
    navController: NavController,
    openFilterByTimeDialog: MutableState<Boolean>,
    openFilterByCuisineDialog: MutableState<Boolean>
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp),
        elevation = mainCardElevation
    ) {
        Column {
            SearchBarCard(viewModel, navController)
            Row {
                SearchButton(
                    Modifier
                        .weight(1f)
                        .padding(10.dp),"Time", Icons.Filled.Alarm
                ) {
                    openFilterByTimeDialog.value = true
                }
                SearchButton(
                    Modifier
                        .weight(1f)
                        .padding(10.dp),"Ingredients", Icons.Filled.LunchDining)
                {
                    //TODO make it search by ingredients
                }
                SearchButton(
                    Modifier
                        .weight(1f)
                        .padding(10.dp),"Cuisine", Icons.Filled.Language)
                {
                    openFilterByCuisineDialog.value = true
                }
            }

        }
    }
}

@Composable
fun SearchBarCard(viewModel: DashboardViewModel, navController: NavController) {
    var searchTerm by remember { mutableStateOf("") }

    Card(
        elevation = nestedCardElevation,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .padding(10.dp, 10.dp)
    ) {
        TextField(
            value = searchTerm,
            onValueChange = { newSearchTerm ->
                searchTerm = newSearchTerm
            },
            singleLine = true,
            placeholder = { Text("Search Recipes") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.filterListRecipesByName(searchTerm)
                    navController.navigate("recipe_list_all")
                }) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                }
            },
            keyboardOptions = KeyboardOptions( imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    viewModel.filterListRecipesByName(searchTerm)
                    navController.navigate("recipe_list_all")
                }
            )
        )
    }
}

@Composable
fun SearchButton(modifier: Modifier, text: String, icon: ImageVector, action: () -> Unit) {
    Card(modifier = modifier
        .aspectRatio(1f)
        .clickable { action() },
        elevation = nestedCardElevation
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(5.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)
                    .padding(10.dp)
            )
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }
    }

}


@Composable
fun AllRecipesCard(
    recipes: List<Recipe>,
    recipeImages: List<RecipeImage>,
    viewModel: DashboardViewModel,
    navController: NavController
) {
    val scrollState = rememberScrollState()

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp),
        elevation = mainCardElevation
    ){
        Column {
            Text(
                "All Recipes",
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp, 5.dp)
            )
            Row (
                modifier = Modifier
                    .horizontalScroll(scrollState),
                verticalAlignment = Alignment.CenterVertically
            ) {
                recipes.forEachIndexed { index, recipe ->
                    RecipeCard(
                        recipe = recipe,
                        if (recipeImages.any { it.recipeId == recipe.id }) {
                            BitmapFactory.decodeFile(
                                recipeImages.first { it.recipeId == recipe.id }.location
                            )
                        } else {
                            null
                        }
                    ) {
                        val recipeId = recipes[index].id
                        viewModel.setRecipeId(recipeId)
                        navController.navigate("recipe_view_dashboard")
                    }
                }
                Box(modifier = Modifier.padding(start = 45.dp, end = 55.dp)) {
                    IconButton(
                        modifier = Modifier
                            .border(2.dp, MaterialTheme.colors.onSurface, shape = CircleShape),
                        onClick = {
                            viewModel.setListRecipesToAll()
                            navController.navigate("recipe_list_all")
                        }) {
                        Icon(Icons.Default.MoreHoriz, contentDescription = "", tint = MaterialTheme.colors.onSurface)
                    }
                }
            }
        }
    }
}


@Composable
fun FilterByTime(
    viewModel: DashboardViewModel,
    openFilterByTimeDialog: MutableState<Boolean>,
    navController: NavController
){
    var searchTime by remember{ mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { openFilterByTimeDialog.value = false },
        title = {
            Text(
                text = "Filter recipes by time",
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            Box {
                Column {
                    TextField(
                        value = searchTime,
                        onValueChange = { newSearchTime ->
                            searchTime = newSearchTime
                        },
                        singleLine = true,
                        placeholder = { Text("Enter time") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = MaterialTheme.colors.surface
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Search
                        )
                    )
                }
            }

        },
        buttons = {
            Row (modifier = Modifier.padding(5.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        openFilterByTimeDialog.value = false
                    },
                ) {
                    Text(
                        text = "Dismiss",
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        viewModel.filterRecipesByTime(searchTime.toInt())
                        navController.navigate("recipe_list_all")
                    },
                ) {
                    Text(
                        text = "Search",
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        },
    )
}


@Composable
fun FilterByCuisine(
    viewModel: DashboardViewModel,
    openFilterByTimeDialog: MutableState<Boolean>,
    navController: NavController
){
    var searchTerm by remember{ mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { openFilterByTimeDialog.value = false },
        title = {
            Text(
                text = "Filter recipes by cuisine",
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            Box {
                Column {
                    TextField(
                        value = searchTerm,
                        onValueChange = { newSearchTerm ->
                            searchTerm = newSearchTerm
                        },
                        singleLine = true,
                        placeholder = { Text("Enter cuisine") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = MaterialTheme.colors.surface
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        )
                    )
                }
            }

        },
        buttons = {
            Row (modifier = Modifier.padding(5.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        openFilterByTimeDialog.value = false
                    },
                ) {
                    Text(
                        text = "Dismiss",
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        viewModel.filterRecipesByCuisine(searchTerm)
                        navController.navigate("recipe_list_all")
                    },
                ) {
                    Text(
                        text = "Search",
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        },
    )
}







@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    SupertasterTheme() {
//        DashboardScreen(FirebaseAuth.getInstance(), NavController())
    }
}
