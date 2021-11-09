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

@Composable
fun SignUp(
    navController: NavController,
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
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
                if (email != "" && password != "") {
                    viewModel.signUpUser(email.trim(), password.trim(),{
                        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                        navController.navigate("profileMainView") {
                            popUpTo("profileMainView") {inclusive = true}
                        }
                    },{
                        Toast.makeText(context, "There is a problem with your email or password", Toast.LENGTH_SHORT).show()
                        email = ""
                        password = ""
                    })
                } else {
                    Toast.makeText(context, "Password or email cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }, modifier = Modifier.padding(10.dp)) {
                Text(text = "Sign Up")
            }
        }
    }
}
