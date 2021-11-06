package com.carvajal.lucas.supertaster.composables

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme
import com.carvajal.lucas.supertaster.viewmodels.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Profile(mAuth: FirebaseAuth) {
    val viewModel = ProfileViewModel(mAuth)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column {
            if (viewModel.isLoggedIn()) {
                Button(onClick = { viewModel.signOut() }) {
                    Text(text = "Sign Out")
                }
            } else {
                Button(onClick = { viewModel.signIn() }) {
                    Text(text = "Sign In")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    SupertasterTheme() {
        Profile(FirebaseAuth.getInstance())
    }
}
