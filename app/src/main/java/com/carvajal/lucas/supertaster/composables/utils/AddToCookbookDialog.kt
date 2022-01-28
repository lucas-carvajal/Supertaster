package com.carvajal.lucas.supertaster.composables.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.carvajal.lucas.supertaster.R
import com.carvajal.lucas.supertaster.composables.mainCardElevation
import com.carvajal.lucas.supertaster.composables.nestedCardElevation
import com.carvajal.lucas.supertaster.data.Recipe
import com.carvajal.lucas.supertaster.viewmodels.RecipeViewListViewModel

@Composable
fun AddToCookbook(
    viewModel: RecipeViewListViewModel,
    openDialog: MutableState<Boolean>,
    recipe: Recipe,
    context: Context
){
    val scrollState = rememberScrollState()
    val cookbooks = viewModel.allCookbooks.observeAsState()

    Dialog(onDismissRequest = { openDialog.value = false }) {
        Card(
            elevation = mainCardElevation
        ) {
            Column(Modifier.padding(5.dp)) {
                Text(
                    text = stringResource(R.string.add_to_cookbook),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(10.dp)
                )
                Divider()
                Box(modifier = Modifier
                    .height(250.dp)
                    .padding(10.dp, 0.dp)
                ) {
                    Column(modifier = Modifier
                        .verticalScroll(scrollState)
                    ) {
                        cookbooks.value?.forEach{ cookbook ->
                            Card(
                                elevation = nestedCardElevation,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth()
                                    .clickable{
                                        viewModel.addRecipeToCookbook(cookbook.id, recipe.id)
                                        openDialog.value = false
                                        Toast.makeText(
                                            context,
                                            "${recipe.title} added to ${cookbook.name}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            ) {
                                Text(
                                    modifier = Modifier.padding(5.dp),
                                    text = cookbook.name,
                                    fontSize = MaterialTheme.typography.h5.fontSize,
                                )
                            }
                        }
                    }
                }
                Divider()
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            openDialog.value = false
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.dismiss),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}