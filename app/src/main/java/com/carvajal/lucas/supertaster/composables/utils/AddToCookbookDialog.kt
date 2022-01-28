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
import com.carvajal.lucas.supertaster.R
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
    val cookbooks = viewModel.allCookbooks.observeAsState().value

    AlertDialog(
        onDismissRequest = { openDialog.value = false },
        title = {
            Text(
                text = stringResource(R.string.add_new_cookbook),
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier
                    .verticalScroll(scrollState)
                ) {
                    Divider()
                    cookbooks?.forEach{ cookbook ->
                        Text(
                            text = cookbook.name,
                            fontSize = MaterialTheme.typography.h5.fontSize,
                            modifier = Modifier.clickable{
                                viewModel.addRecipeToCookbook(cookbook.id, recipe.id)
                                openDialog.value = false
                                Toast.makeText(
                                    context,
                                    "${recipe.title} added to ${cookbook.name}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                        Divider()
                    }
                }
            }

        },
        buttons = {
            Row {
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
        },
    )
}