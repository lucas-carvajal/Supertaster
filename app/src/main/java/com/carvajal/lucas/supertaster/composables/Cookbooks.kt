package com.carvajal.lucas.supertaster.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.carvajal.lucas.supertaster.R
import com.carvajal.lucas.supertaster.composables.utils.TopRow
import com.carvajal.lucas.supertaster.data.Cookbook
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme
import com.carvajal.lucas.supertaster.viewmodels.CookbookViewModel

@Composable
fun CookbooksScreen(viewModel: CookbookViewModel, navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val openDialog = remember { mutableStateOf(false) }
    
    val cookbooks = viewModel.allCookbooks.observeAsState()

    if (openDialog.value) {
        NewCookbookDialog(viewModel, openDialog)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 0.dp)
    ) {
        Column (modifier = Modifier
            .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopRow(heading = stringResource(R.string.my_cookbooks_caps), icon = Icons.Default.Add) {
                openDialog.value = true
            }

            if (!cookbooks.value.isNullOrEmpty()) {
                cookbooks.value?.forEach { cookbook ->
                    CookbookEntry(cookbook, viewModel, navController)
                }
            } else {
                Text(
                    text = stringResource(R.string.tap_to_create_first_cookbook),
                    modifier = Modifier.padding(top = 30.dp)
                )
            }

            Spacer(Modifier.padding(5.dp))
        }
    }
}

@Composable
fun CookbookEntry(cookbook: Cookbook, viewModel: CookbookViewModel, navController: NavController) {
    Card(
        elevation = mainCardElevation,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .clickable {
                viewModel.setCurrentCookbook(cookbook.id)
                navController.navigate("recipe_list_cookbooks")
            }
    ) {
        Text(
            text = cookbook.name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp)
        )
    }
}

@Composable
fun NewCookbookDialog(viewModel: CookbookViewModel, openDialog: MutableState<Boolean>) {
    var newCookbookName = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { openDialog.value = false },
        title = {
            Text(
                text = stringResource(R.string.add_new_cookbook),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp)
            )
        },
        text = {
            TextField(
                value = newCookbookName.value,
                onValueChange = { newCookbookName.value = it },
                singleLine = true,
                modifier = Modifier.padding(5.dp)
            )
        },
        buttons = {
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        openDialog.value = false
                    },
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(text = stringResource(R.string.dismiss))
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        viewModel.addCookbook(newCookbookName.value)
                        openDialog.value = false
                    },
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(text = stringResource(R.string.add))
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CookbooksScreenPreview() {
    SupertasterTheme {
        //CookbooksScreen(CookbookViewModel())
    }
}
