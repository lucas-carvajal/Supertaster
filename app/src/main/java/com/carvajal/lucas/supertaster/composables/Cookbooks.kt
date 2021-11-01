package com.carvajal.lucas.supertaster.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme
import com.carvajal.lucas.supertaster.viewmodels.CookbookViewModel
import com.carvajal.lucas.supertaster.viewmodels.DashboardViewModel

@Composable
fun CookbooksScreen() {
    val viewModel = CookbookViewModel()
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
            TopRow(heading = "MY COOKBOOKS", icon = Icons.Default.Add) {
                Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show()
            }
//            SuggestionsCard(viewModel.getSuggestions())
//            SearchCard()
//            AllRecipesCard(viewModel.getSampleRecipes())
//            Spacer(Modifier.padding(5.dp))
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CookbooksScreenPreview() {
    SupertasterTheme {
        CookbooksScreen()
    }
}
