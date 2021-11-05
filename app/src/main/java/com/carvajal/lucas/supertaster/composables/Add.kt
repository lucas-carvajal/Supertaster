package com.carvajal.lucas.supertaster.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme
import com.carvajal.lucas.supertaster.viewmodels.AddViewModel

@Composable
fun AddScreen() {
    val viewModel = AddViewModel()
    val scrollState = rememberScrollState()

    var title by remember { mutableStateOf("")}
    var cuisine by remember { mutableStateOf("") }

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
                SingleInputField(cuisine, { title = it }, "Cuisine")
            }
            Section(title = "Type of Meal") {
                ChooseTypeOfMealButton()
            }
            Section(title = "Servings") {
                ServingsRow()
            }
            Section(title = "Prep Time") {
                PrepTimeRow()
            }
            Section(title = "Cook Time") {
                CookTimeRow()
            }
            Section(title = "Ingredients") {
                IngredientsSection()
            }
            Section(title = "Steps") {
                StepsSection()
            }
            //TODO Save Button
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
    LazyRow() {
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
fun ChooseTypeOfMealButton() {
    //TODO
}

@Composable
fun ServingsRow() {
    //TODO
}

@Composable
fun PrepTimeRow() {
    //TODO
}

@Composable
fun CookTimeRow() {
    //TODO
}

@Composable
fun IngredientsSection() {
    //TODO
}

@Composable
fun StepsSection() {
    //TODO
}


@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    SupertasterTheme {
        AddScreen()
    }
}
