package com.carvajal.lucas.supertaster.composables

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carvajal.lucas.supertaster.ui.theme.RedPink85
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme
import com.carvajal.lucas.supertaster.viewmodels.AddViewModel

@Composable
fun AddScreen(viewModel: AddViewModel) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var title by remember { mutableStateOf("")}
    var cuisine by remember { mutableStateOf("") }
    var servings by remember { mutableStateOf(2) }
    var prepTime by remember { mutableStateOf(5) }
    var cookTime by remember { mutableStateOf(5) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Text(
                text = "ADD A RECIPE",
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold
            )
            Section(title = "Title") {
                SingleInputField(title, { title = it },"Title")
            }
            Section(title = "Photos") {
                PhotoRow(viewModel.getPhotos())
            }
            Section(title = "Cuisine") {
                SingleInputField(cuisine, { cuisine = it }, "Cuisine")
            }
            Section(title = "Type of Meal") {
                ChooseTypeOfMealButton(viewModel.getMealTypes())
            }
            Section(title = "Servings") {
                ServingsRow(servings,
                    { servings++ },
                    {
                        if (servings != 1) {
                            servings--
                        }
                    }
                )
            }
            Section(title = "Prep Time") {
                PrepTimeRow(prepTime,
                    { prepTime += 5},
                    {
                        if (prepTime != 0) {
                            prepTime -= 5
                        }
                    }
                )
            }
            Section(title = "Cook Time") {
                CookTimeRow(cookTime,
                    { cookTime += 5},
                    {
                        if (cookTime != 0) {
                            cookTime -= 5
                        }
                    }
                )
            }
            Section(title = "Ingredients") {
                IngredientsSection(viewModel)
            }
            Section(title = "Steps") {
                StepsSection(viewModel)
            }

            Button(onClick = {
                val success = viewModel.saveRecipe()
                if (success) {
                    Toast.makeText(context, "Recipe $title saved successfully", Toast.LENGTH_SHORT).show()
                    cleanup()
                } else {
                    Toast.makeText(context, "Error: Recipe $title could not be saved", Toast.LENGTH_SHORT).show()
                }
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = RedPink85),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = "Save Recipe",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun Section(title: String, content: @Composable() () -> Unit) {
    Column(modifier = Modifier
        .padding(0.dp, 10.dp)
        .fillMaxWidth())
    {
        Text(
            text = title,
            fontWeight = FontWeight.Bold
        )
        content()
    }
}

@Composable
fun SingleInputField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(text = label)},
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun PhotoRow(photos: List<Int>) {
    LazyRow {
        items(photos) { photo ->
            Card(
                modifier = Modifier.padding(10.dp),
                elevation = nestedCardElevation
            ) {
                Image(
                    painter = painterResource(id = photo),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .width(80.dp)
                        .aspectRatio(1f)
                        .clickable { /* TODO make it delete photo */ }
                )
            }
        }
        item {
            Card(
                modifier = Modifier.padding(10.dp),
                elevation = nestedCardElevation
            ) {
                Image(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .width(80.dp)
                        .aspectRatio(1f)
                        .clickable { /* TODO make it add photos */ }
                )
            }
        }
    }
}

@Composable
fun ChooseTypeOfMealButton(mealTypes: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    Card(
        elevation = nestedCardElevation,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Text(
            mealTypes[selectedIndex],
            modifier = Modifier
                .clickable { expanded = true }
                .padding(10.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            mealTypes.forEachIndexed { index, mealType ->
                DropdownMenuItem(
                    onClick = {
                        selectedIndex = index
                        expanded = false
                    }
                ) {
                    Text(text = mealType)
                }
            }
        }
    }
}

@Composable
fun ServingsRow(servings: Int, incrementServings: () -> Unit, decrementServings: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { decrementServings() },
            modifier = Modifier.weight(3f)
        ) {
            Text(
                text = "-",
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
            )
        }
        Card(
            elevation = nestedCardElevation,
            modifier = Modifier
                .height(42.dp)
                .padding(10.dp, 0.dp)
                .weight(9f)
        ) {
            Text(
                text = servings.toString(),
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = { incrementServings() },
            Modifier.weight(3f)
        ) {
            Text(
                text = "+",
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun PrepTimeRow(prepTime: Int, incrementPrepTime: () -> Unit, decrementPrepTime: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { decrementPrepTime() },
            modifier = Modifier.weight(3f)
        ) {
            Text(
                text = "-",
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
            )
        }
        Card(
            elevation = nestedCardElevation,
            modifier = Modifier
                .height(42.dp)
                .padding(10.dp, 0.dp)
                .weight(9f)
        ) {
            Text(
                text = "${prepTime.toString()} min",
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = { incrementPrepTime() },
            Modifier.weight(3f)
        ) {
            Text(
                text = "+",
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun CookTimeRow(cookTime: Int, incrementCookTime: () -> Unit, decrementCookTime: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { decrementCookTime() },
            modifier = Modifier.weight(3f)
        ) {
            Text(
                text = "-",
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
            )
        }
        Card(
            elevation = nestedCardElevation,
            modifier = Modifier
                .height(42.dp)
                .padding(10.dp, 0.dp)
                .weight(9f)
        ) {
            Text(
                text = "${cookTime.toString()} min",
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = { incrementCookTime() },
            Modifier.weight(3f)
        ) {
            Text(
                text = "+",
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun IngredientsSection(viewModel: AddViewModel) {
    var ingredients: List<Pair<String, String>> by rememberSaveable { mutableStateOf( viewModel.getIngredients() ) }

    Column(modifier = Modifier.padding(top = 10.dp)) {

        ingredients.forEachIndexed { index, recipeIngredient ->
            var name by remember { mutableStateOf(recipeIngredient.first) }
            var amount by remember { mutableStateOf(recipeIngredient.second) }
            
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { newName ->
                        name = newName
                        val newIngredient = Pair(
                            newName, recipeIngredient.second
                        )

                        var newIngredientsList: MutableList<Pair<String, String>> = ingredients.toMutableList()
                        newIngredientsList[index] = newIngredient
                        ingredients = newIngredientsList
                        viewModel.setIngredients(ingredients as MutableList<Pair<String, String>>)
                    },
                    singleLine = true,
                    label = { Text(text = "Ingredient")},
                    modifier = Modifier
                        .weight(5f)
                        .padding(5.dp)
                )

                OutlinedTextField(
                    value = amount,
                    onValueChange = { newAmount ->
                        amount = newAmount

                        val newIngredient = Pair(
                            recipeIngredient.first, newAmount
                        )

                        var newIngredientsList: MutableList<Pair<String, String>> = ingredients.toMutableList()
                        newIngredientsList[index] = newIngredient
                        ingredients = newIngredientsList
                        viewModel.setIngredients(ingredients as MutableList<Pair<String, String>>)
                    },
                    singleLine = true,
                    label = { Text(text = "Amount")},
                    modifier = Modifier
                        .weight(3f)
                        .padding(5.dp)
                )
            }
        }

        Button(onClick = {
                ingredients = ingredients + Pair("", "")
                viewModel.setIngredients(ingredients as MutableList<Pair<String, String>>)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = "+")
        }
    }
}

@Composable
fun StepsSection(viewModel: AddViewModel) {
    var steps: List<Triple<Int, String, String>> by rememberSaveable { mutableStateOf( viewModel.getSteps() ) }
    
    Column(modifier = Modifier.padding(top = 10.dp)) {
        steps.forEachIndexed { index, recipeStep ->
            var description by remember { mutableStateOf(recipeStep.second) }
            var extraNotes by remember { mutableStateOf(recipeStep.third) }

            Card(
                modifier = Modifier.padding(10.dp),
                elevation = nestedCardElevation
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                ) {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { newDescription ->
                            description = newDescription

                            val newStep = Triple(
                                recipeStep.first, newDescription, recipeStep.third
                            )

                            var newStepsList: MutableList<Triple<Int, String, String>> = steps.toMutableList()
                            newStepsList[index] = newStep
                            steps = newStepsList
                            viewModel.setSteps(steps as MutableList<Triple<Int, String, String>>)
                        },
                        singleLine = false,
                        label = { Text(text = "Step ${recipeStep.first} - Description")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    )

                    OutlinedTextField(
                        value = extraNotes,
                        onValueChange = { newExtraNotes ->
                            extraNotes = newExtraNotes

                            val newStep = Triple(
                                recipeStep.first, recipeStep.second, newExtraNotes
                            )

                            var newStepsList: MutableList<Triple<Int, String, String>> = steps.toMutableList()
                            newStepsList[index] = newStep
                            steps = newStepsList
                            viewModel.setSteps(steps as MutableList<Triple<Int, String, String>>)
                        },
                        singleLine = false,
                        label = { Text(text = "Step ${recipeStep.first} - Extra Notes")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    )
                }
            }
        }

        Button(onClick = {
            steps = steps + Triple(steps.size + 1, "", "")
            viewModel.setSteps(steps as MutableList<Triple<Int, String, String>>)
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = "+")
        }
    }
}

private fun cleanup() {
    //TODO reset all values in viewModel and here
}


@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    SupertasterTheme {
        AddScreen(AddViewModel())
    }
}
