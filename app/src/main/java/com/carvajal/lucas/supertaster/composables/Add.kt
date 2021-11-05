package com.carvajal.lucas.supertaster.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
                PhotoRow()
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
fun PhotoRow() {
    //TODO
}

@Composable
fun ChooseTypeOfMealButton() {
    //TODO
}

@Composable
fun ServingsRow() {
    //TODO
}


@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    SupertasterTheme {
        AddScreen()
    }
}
