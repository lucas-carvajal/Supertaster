package com.carvajal.lucas.supertaster.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.carvajal.lucas.supertaster.viewmodels.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileMainView(
    navController: NavController,
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val mAuth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column {
            if (viewModel.isLoggedIn) {
                Text(
                    text = "Signed in with:",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = mAuth.currentUser.email,
                    modifier = Modifier.padding(10.dp)
                )
                Button(
                    onClick = {
                        viewModel.signOutUser()
                        navController.navigate("profileMainView") {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(text = "Sign Out")
                }
            } else {
                Button(
                    onClick = { navController.navigate("signIn") },
                    modifier = Modifier.width(200.dp).padding(10.dp)
                ) {
                    Text(text = "Sign In")
                }
                Button(
                    onClick = { navController.navigate("signUp") },
                    modifier = Modifier.width(200.dp).padding(10.dp)
                ) {
                    Text(text = "Sign Up")
                }
            }
        }
    }
}