package com.carvajal.lucas.supertaster.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.carvajal.lucas.supertaster.viewmodels.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignIn(navController: NavController) {
    val viewModel = ProfileViewModel(FirebaseAuth.getInstance())
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column() {
            OutlinedTextField(
                value = email,
                onValueChange = { newEmail ->
                    email = newEmail
                },
                singleLine = true,
                label = {
                    Text("Email")
                },
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { newPassword ->
                    password = newPassword
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                label = {
                    Text("Password")
                },
                trailingIcon = {
                    val image = if (passwordVisibility)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(imageVector  = image, "")
                    }
                },
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            )

            Button(onClick = {
                viewModel.signInUser(email, password,{
                    Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show()
                    navController.navigate("profileMainView")
                },{
                    Toast.makeText(context, "FAIL", Toast.LENGTH_SHORT).show()
                    email = ""
                    password = ""
                })
            }) {
                Text(text = "Sign In")
            }
        }
    }
}
