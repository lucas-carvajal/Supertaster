package com.carvajal.lucas.supertaster.composables

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carvajal.lucas.supertaster.R
import com.carvajal.lucas.supertaster.ui.theme.RedPink85
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme
import com.carvajal.lucas.supertaster.viewmodels.AddViewModel
import com.carvajal.lucas.supertaster.viewmodels.Ingredient
import com.carvajal.lucas.supertaster.viewmodels.Step


const val REQUEST_IMAGE_CAPTURE = 1

private lateinit var addViewModel: AddViewModel

@Composable
fun AddScreen(viewModel: AddViewModel) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    addViewModel = viewModel

    var title by remember { mutableStateOf(viewModel.title)}
    var cuisine by remember { mutableStateOf(viewModel.cuisine) }
    var servings by remember { mutableStateOf(viewModel.servings) }
    var prepTime by remember { mutableStateOf(viewModel.prepTime) }
    var cookTime by remember { mutableStateOf(viewModel.cookTime) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(R.string.add_a_recipe_caps),
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold
            )
            Section(title = stringResource(R.string.title)) {
                SingleInputField(
                    title,
                    {
                        title = it
                        viewModel.title = it
                    },
                    stringResource(R.string.title)
                )
            }
            Section(title = stringResource(R.string.photos)) {
                PhotoRow(viewModel, context)
            }
            Section(title = stringResource(R.string.cuisine)) {
                SingleInputField(
                    cuisine,
                    {
                        cuisine = it
                        viewModel.cuisine = it
                    },
                    stringResource(R.string.cuisine)
                )
            }
            Section(title = stringResource(R.string.type_of_meal)) {
                ChooseTypeOfMealButton(viewModel)
            }
            Section(title = stringResource(R.string.servings)) {
                ServingsRow(servings,
                    {
                        servings++
                        viewModel.servings++
                    },
                    {
                        if (servings != 1) {
                            servings--
                            viewModel.servings--
                        }
                    }
                )
            }
            Section(title = stringResource(R.string.prep_time)) {
                PrepTimeRow(prepTime,
                    {
                        prepTime += 5
                        viewModel.prepTime += 5
                    },
                    {
                        if (prepTime != 0) {
                            prepTime -= 5
                            viewModel.prepTime -= 5
                        }
                    }
                )
            }
            Section(title = stringResource(R.string.cook_time)) {
                CookTimeRow(cookTime,
                    {
                        cookTime += 5
                        viewModel.cookTime += 5
                    },
                    {
                        if (cookTime != 0) {
                            cookTime -= 5
                            viewModel.cookTime -= 5
                        }
                    }
                )
            }
            Section(title = stringResource(R.string.ingredients)) {
                IngredientsSection(viewModel)
            }
            Section(title = stringResource(R.string.steps)) {
                StepsSection(viewModel)
            }

            Button(onClick = {
                if (viewModel.title.trim().isEmpty()) {
                    Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                // TODO comment in again
//                } else if (viewModel.typeOfMealIndex == 0) {
//                    Toast.makeText(context, "Please select a type of meal", Toast.LENGTH_SHORT).show()
                } else {
                    val success = viewModel.saveRecipe(context)
                    if (success) {
                        Toast.makeText(context, "Recipe $title saved successfully", Toast.LENGTH_SHORT).show()
                        cleanup()
                    } else {
                        Toast.makeText(context, "Error: Recipe $title could not be saved", Toast.LENGTH_SHORT).show()
                    }
                }
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = RedPink85),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = stringResource(R.string.save_recipe),
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
fun PhotoRow(viewModel: AddViewModel, context: Context) {
    val photos = viewModel.recipePhotos

    LazyRow {
        itemsIndexed(photos) { index, photo ->
            Card(
                modifier = Modifier.padding(10.dp),
                elevation = nestedCardElevation
            ) {
                Image(
                    bitmap = photo.asImageBitmap(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .width(80.dp)
                        .aspectRatio(1f)
                        .clickable { viewModel.removeRecipePhoto(index) }
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
                        .clickable { addPhoto(context) }
                )
            }
        }
    }
}

private fun addPhoto(context: Context) {
    val packageManager = context.packageManager
    val hasCamera = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)

    if (hasCamera) {
        dispatchTakePictureIntent(context)
    } else {
        Toast.makeText(context, "Camera feature is not available on this device", Toast.LENGTH_SHORT).show()
    }
}

private fun dispatchTakePictureIntent(context: Context) {
    val activity: Activity = context as Activity
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    try {
        activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "Camera app not found", Toast.LENGTH_SHORT).show()
    }
}

fun addRecipeImage(bitmap: Bitmap) {
    addViewModel.addRecipePhotos(bitmap)
}


@Composable
fun ChooseTypeOfMealButton(viewModel: AddViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var typeOfMealIndex by remember { mutableStateOf(viewModel.typeOfMealIndex) }

    val mealTypes = viewModel.getMealTypes()

    Card(
        elevation = nestedCardElevation,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Text(
            mealTypes[typeOfMealIndex],
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
                        typeOfMealIndex = index
                        viewModel.typeOfMealIndex = index
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
    val ingredients = viewModel.ingredients

    Column(modifier = Modifier.padding(top = 10.dp)) {
        
        ingredients.forEachIndexed { index, recipeIngredient ->
            var name by remember { mutableStateOf(recipeIngredient.name) }
            var amount by remember { mutableStateOf(recipeIngredient.amount) }

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { newName ->
                        name = newName
                        val newIngredient = Ingredient(newName, recipeIngredient.amount)
                        viewModel.updateIngredient(index, newIngredient)
                    },
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.ingredient))},
                    modifier = Modifier
                        .weight(5f)
                        .padding(5.dp)
                )

                OutlinedTextField(
                    value = amount,
                    onValueChange = { newAmount ->
                        amount = newAmount
                        val newIngredient = Ingredient(recipeIngredient.name, newAmount)
                        viewModel.updateIngredient(index, newIngredient)
                    },
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.amount))},
                    modifier = Modifier
                        .weight(3f)
                        .padding(5.dp)
                )
            }
        }

        Button(onClick = {
                viewModel.addIngredient(Ingredient("", ""))
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
    val steps = viewModel.steps
    
    Column(modifier = Modifier.padding(top = 10.dp)) {
        steps.forEachIndexed { index, recipeStep ->
            var description by remember { mutableStateOf(recipeStep.description) }
            var extraNotes by remember { mutableStateOf(recipeStep.extraNote) }

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
                            val newStep = Step(recipeStep.sequence, newDescription, recipeStep.extraNote)
                            viewModel.updateSteps(index, newStep)
                        },
                        singleLine = false,
                        label = { Text(text = "Step ${recipeStep.sequence} - Description")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    )

                    OutlinedTextField(
                        value = extraNotes,
                        onValueChange = { newExtraNotes ->
                            extraNotes = newExtraNotes
                            val newStep = Step(recipeStep.sequence, recipeStep.description, newExtraNotes)
                            viewModel.updateSteps(index, newStep)
                        },
                        singleLine = false,
                        label = { Text(text = "Step ${recipeStep.sequence} - Extra Notes")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    )
                }
            }
        }

        Button(onClick = {
            viewModel.addStep(Step(steps.size + 1, "", ""))
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
        //AddScreen(AddViewModel())
    }
}
