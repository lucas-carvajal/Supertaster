package com.carvajal.lucas.supertaster.auth

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
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
            if (viewModel.isLoggedIn.value == true) {
                Text(
                    text = "Signed in with: ${mAuth.currentUser.email}",
                    modifier = Modifier.padding(10.dp)
                )
                Button(
                    onClick = {
                        viewModel.signOutUser()
                        Log.i("PROFILEMAINVIEW", viewModel.isLoggedIn.toString())
                        //TODO recompose
                    },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(text = "Sign Out")
                }

                //TODO remove
                Button(onClick = {
                    Log.i("PROFILEMAINVIEW", viewModel.isLoggedIn.value.toString())
                }) {
                    Text(text = "Test Bool")
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