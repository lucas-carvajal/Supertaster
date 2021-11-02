package com.carvajal.lucas.supertaster.composables

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carvajal.lucas.supertaster.data.Cookbook
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme
import com.carvajal.lucas.supertaster.viewmodels.CookbookViewModel

@Composable
fun CookbooksScreen() {
    val viewModel = CookbookViewModel()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    
    val cookbooks = viewModel.getCookbooks()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column (modifier = Modifier
            .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopRow(heading = "MY COOKBOOKS", icon = Icons.Default.Add) {
                Toast.makeText(context, "Add", Toast.LENGTH_SHORT).show()
            }
            if (cookbooks.isNotEmpty()) {
                cookbooks.forEach { cookbook ->
                    CookbookEntry(cookbook)
                }
            } else {
                Text(
                    text = "Tap the + to create your first cookbook",
                    modifier = Modifier.padding(top = 30.dp)
                )
            }
            Spacer(Modifier.padding(5.dp))
        }
    }
}

@Composable
fun CookbookEntry(cookbook: Cookbook) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp),
        elevation = mainCardElevation
    ) {
        Text(
            text = cookbook.name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CookbooksScreenPreview() {
    SupertasterTheme {
        CookbooksScreen()
    }
}
