package com.carvajal.lucas.supertaster.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
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


val mainCardElevation = 3.dp
val nestedCardElevation = 1.dp

@Composable
fun DashboardScreen() {
    val viewModel = DashboardViewModel()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(MaterialTheme.colors.surface),
    ) {
        Column() {
            TopRow(viewModel = viewModel, context = context)
            SuggestionsCard(viewModel.getSuggestions(), context = context)
        }
    }
}


@Composable
fun TopRow(viewModel: DashboardViewModel, context: Context) {
    Row (verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = viewModel.getGreeting(),
            color = MaterialTheme.colors.onSurface,
            fontSize = MaterialTheme.typography.h5.fontSize
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            modifier = Modifier.border(2.dp, MaterialTheme.colors.onSurface, shape = CircleShape),
            onClick = {
                Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show()
            }) {
                Icon(Icons.Default.Person, contentDescription = "", tint = MaterialTheme.colors.onSurface)
        }
    }
}


@Composable
fun SuggestionsCard(recipeSuggestions: List<Recipe>, context: Context) {
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
                modifier = Modifier.padding(5.dp)
            )
            Row (
                modifier = Modifier
                    .horizontalScroll(scrollState)
            ) {
                recipeSuggestions.forEach { recipe ->
                    RecipeCard(recipe = recipe, context = context)
                }
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, context: Context) {
    Card (
        modifier = Modifier
            .padding(5.dp)
            .width(140.dp)
            .height(210.dp),
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
                    .clip(RoundedCornerShape(5.dp))
                    .weight(0.1f)
                )
            Text(
                text = recipe.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
fun SearchCard() {
    //TODO
}


@Composable
fun AllRecipesCard() {
    //TODO
}


@Preview
@Composable
fun DashboardScreenPreview() {
    SupertasterTheme {
        DashboardScreen()
    }
}

//@Preview
//@Composable
//fun TopRowPreview() {
//    SupertasterTheme {
//        TopRow(DashboardViewModel(), LocalContext.current)
//    }
//}
