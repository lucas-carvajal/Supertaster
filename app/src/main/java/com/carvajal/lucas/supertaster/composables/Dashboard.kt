package com.carvajal.lucas.supertaster.composables

import android.content.Context
import android.util.Log
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carvajal.lucas.supertaster.R
import com.carvajal.lucas.supertaster.data.Recipe
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme
import com.carvajal.lucas.supertaster.viewmodels.DashboardViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction


val mainCardElevation = 5.dp
val nestedCardElevation = 3.dp

@Composable
fun DashboardScreen() {
    val viewModel = DashboardViewModel()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column (modifier = Modifier
            .verticalScroll(scrollState)
        ) {
            TopRow(heading = viewModel.getGreeting(), icon = Icons.Default.Person) {
                Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show()
            }
            SuggestionsCard(viewModel.getSuggestions())
            SearchCard()
            AllRecipesCard(viewModel.getSampleRecipes())
            Spacer(Modifier.padding(5.dp))
        }
    }
}


@Composable
fun SuggestionsCard(recipeSuggestions: List<Recipe>) {
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
                recipeSuggestions.forEach { recipe ->
                    RecipeCard(recipe = recipe, { /* TODO navigate to recipe */})
                }
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, action: () -> Unit) {
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
            Image(
                painter = painterResource(id = R.drawable.tacos_al_pastor),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .weight(0.1f)
                )
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
fun SearchCard() {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp),
        elevation = mainCardElevation
    ) {
        Column {
            SearchBarCard()
            Row {
                SearchButton(
                    Modifier
                        .weight(1f)
                        .padding(10.dp),"Time", Icons.Filled.Alarm) {
                    //TODO make it search by time
                }
                SearchButton(
                    Modifier
                        .weight(1f)
                        .padding(10.dp),"Ingredients", Icons.Filled.LunchDining) {
                    //TODO make it search by time
                }
                SearchButton(
                    Modifier
                        .weight(1f)
                        .padding(10.dp),"Cuisine", Icons.Filled.Language) {
                    //TODO make it search by time
                }
            }

        }
    }
}

@Composable
fun SearchBarCard() {
    var searchTerm by remember { mutableStateOf("")}

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
                    /*TODO make it search*/
                    Log.d("TEST TAG", "searched: $searchTerm")
                }) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                }
            },
            keyboardOptions = KeyboardOptions( imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    /*TODO make it search*/
                    Log.d("TEST TAG", "searched: $searchTerm")
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
fun AllRecipesCard(recipes: List<Recipe>) {
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
                recipes.forEach { recipe ->
                    RecipeCard(recipe = recipe, { /* TODO navigate to recipe */})
                }
                Box(modifier = Modifier.padding(start = 45.dp, end = 55.dp)) {
                    IconButton(
                        modifier = Modifier
                            .border(2.dp, MaterialTheme.colors.onSurface, shape = CircleShape),
                        onClick = {
                            //TODO open all recipes page
                        }) {
                        Icon(Icons.Default.MoreHoriz, contentDescription = "", tint = MaterialTheme.colors.onSurface)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    SupertasterTheme() {
        DashboardScreen()
    }
}
