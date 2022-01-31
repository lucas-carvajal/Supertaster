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
import androidx.compose.ui.Alignment
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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.carvajal.lucas.supertaster.R
import com.carvajal.lucas.supertaster.ui.theme.RedPink85
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme
import com.carvajal.lucas.supertaster.util.RecipeViewMode
import com.carvajal.lucas.supertaster.util.getTypeOfMeal
import com.carvajal.lucas.supertaster.util.typeOfMealMap
import com.carvajal.lucas.supertaster.viewmodels.AddViewModel
import com.carvajal.lucas.supertaster.viewmodels.Ingredient
import com.carvajal.lucas.supertaster.viewmodels.Step


const val REQUEST_IMAGE_CAPTURE = 1

private lateinit var addViewModel: AddViewModel

@Composable
fun AddScreen(viewModel: AddViewModel, viewMode: RecipeViewMode, navController: NavController, backStackEntry: NavBackStackEntry?) {

    val recipeId: Long = backStackEntry?.arguments?.getLong("recipeId") ?: -1

    if (recipeId.toInt() != -1) {
        viewModel.loadData(recipeId)

        AddScreenLoadingManager(viewModel, viewMode, navController, recipeId)
    } else {
        AddScreenContent(viewModel, viewMode, navController, null)
    }
}

@Composable
fun AddScreenLoadingManager(viewModel: AddViewModel, viewMode: RecipeViewMode, navController: NavController, recipeId: Long?) {
    if (viewModel.isLoading.value){
        LoadingScreen()
    } else {
        AddScreenContent(viewModel, viewMode, navController, recipeId)
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }

    }
}

@Composable
fun AddScreenContent(viewModel: AddViewModel, viewMode: RecipeViewMode, navController: NavController, recipeId: Long?) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val openDeleteContextMenu = remember { mutableStateOf(false) }

    addViewModel = viewModel

    if (openDeleteContextMenu.value) {
        DeleteRecipeContextMenu(viewModel, openDeleteContextMenu, navController, recipeId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 0.dp)
    ) {
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(R.string.add_a_recipe_caps),
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )
            Section(title = stringResource(R.string.title)) {
                SingleInputField(
                    viewModel.title.value,
                    {
                        viewModel.setTitle(it)
                    },
                    stringResource(R.string.title)
                )
            }
            Section(title = stringResource(R.string.photos)) {
                PhotoRow(viewModel, context)
            }
            Section(title = stringResource(R.string.cuisine)) {
                SingleInputField(
                    viewModel.cuisine.value,
                    {
                        viewModel.setCuisine(it)
                    },
                    stringResource(R.string.cuisine)
                )
            }
            Section(title = stringResource(R.string.type_of_meal)) {
                ChooseTypeOfMealButton(
                    viewModel.typeOfMealIndex.value
                ) { index ->
                    viewModel.setTypeOfMealIndex(index)
                }
            }
            Section(title = stringResource(R.string.servings)) {
                ServingsRow(viewModel.servings.value,
                    {
                        viewModel.setServings(viewModel.servings.value + 1)
                    },
                    {
                        if (viewModel.servings.value != 1) {
                            viewModel.setServings(viewModel.servings.value - 1)
                        }
                    }
                )
            }
            Section(title = stringResource(R.string.prep_time)) {
                PrepTimeRow(viewModel.prepTime.value,
                    {
                        viewModel.setPrepTime(viewModel.prepTime.value + 5)
                    },
                    {
                        if (viewModel.prepTime.value != 0) {
                            viewModel.setPrepTime(viewModel.prepTime.value - 5)
                        }
                    }
                )
            }
            Section(title = stringResource(R.string.cook_time)) {
                CookTimeRow(viewModel.cookTime.value,
                    {
                        viewModel.setCookTime(viewModel.cookTime.value + 5)
                    },
                    {
                        if (viewModel.cookTime.value != 0) {
                            viewModel.setCookTime(viewModel.cookTime.value - 5)
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
                if (viewModel.title.value.trim().isEmpty()) {
                    Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                } else if (viewModel.typeOfMealIndex.value == 0) {
                    Toast.makeText(context, "Please select a type of meal", Toast.LENGTH_SHORT).show()
                } else {

                    val savedRecipe: String? = if (recipeId != null) {
                        viewModel.saveRecipe(context, recipeId)
                    } else {
                        viewModel.saveRecipe(context, null)
                    }

                    if (savedRecipe != null) {
                        Toast.makeText(context, "Recipe $savedRecipe saved successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Error: Recipe could not be saved", Toast.LENGTH_SHORT).show()
                    }

                    if (viewMode == RecipeViewMode.EDIT) {
                        navController.popBackStack()
                    }
                }
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = RedPink85),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text =
                        if (viewMode == RecipeViewMode.ADD) {
                            stringResource(R.string.save_recipe)
                        } else {
                            "Save Changes"
                        },
                    color = Color.White
                )
            }
            if (recipeId != null) {
                Button(onClick = {
                    openDeleteContextMenu.value = true
                },
                    colors = ButtonDefaults.buttonColors(backgroundColor = RedPink85),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Delete Recipe",
                        color = Color.White
                    )
                }
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
    //TODO new with CameraX
}


private fun addPhoto1(context: Context) {
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
fun ChooseTypeOfMealButton(typeOfMealIndex: Int, setTypeOfMealIndex: (index: Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        elevation = nestedCardElevation,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Text(
            getTypeOfMeal(typeOfMealIndex) ?: "",
            modifier = Modifier
                .clickable { expanded = true }
                .padding(10.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            for ((index, mealType) in typeOfMealMap) {
                DropdownMenuItem(
                    onClick = {
                        setTypeOfMealIndex(index)
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

@Composable
fun DeleteRecipeContextMenu(
    viewModel: AddViewModel,
    openDeleteContextMenu: MutableState<Boolean>,
    navController: NavController,
    recipeId: Long?
) {
    AlertDialog(
        onDismissRequest = { openDeleteContextMenu.value = false },
        title = {
            Text(
                text = "Delete Recipe?",
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            Text(text = "Do you want to delete ${viewModel.title.value}?")
        },
        buttons = {
            Row (modifier = Modifier.padding(5.dp, 5.dp, 5.dp, 15.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        openDeleteContextMenu.value = false
                    },
                ) {
                    Text(
                        text = stringResource(R.string.dismiss),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        navController.popBackStack()
                        navController.popBackStack()
                        viewModel.deleteRecipe(recipeId)
                    },
                ) {
                    Text(
                        text = "Delete",
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    SupertasterTheme {
        //AddScreen(AddViewModel())
    }
}
