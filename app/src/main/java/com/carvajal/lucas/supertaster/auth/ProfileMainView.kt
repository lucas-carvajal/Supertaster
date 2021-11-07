package com.carvajal.lucas.supertaster.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.carvajal.lucas.supertaster.viewmodels.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileMainView(navController: NavController) {
    val viewModel = ProfileViewModel(FirebaseAuth.getInstance())

    val mAuth = FirebaseAuth.getInstance()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column {
            if (viewModel.isLoggedIn) {
                Text(text = "Signed in with: ${mAuth.currentUser.email}")
                Button(onClick = {
                    viewModel.signOutUser()
                    //TODO recompose
                }) {
                    Text(text = "Sign Out")
                }
            } else {
                Button(onClick = { navController.navigate("signIn") }) {
                    Text(text = "Sign In")
                }
                Button(onClick = { navController.navigate("signUp") }) {
                    Text(text = "Sign Up")
                }
            }
        }
    }
}