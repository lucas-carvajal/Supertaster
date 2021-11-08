package com.carvajal.lucas.supertaster.composables

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.carvajal.lucas.supertaster.auth.ProfileMainView
import com.carvajal.lucas.supertaster.auth.SignIn
import com.carvajal.lucas.supertaster.auth.SignUp
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme
import com.carvajal.lucas.supertaster.viewmodels.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Profile(
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "profileMainView") {
        composable("profileMainView") { ProfileMainView(navController, viewModel) }
        composable("signUp") { SignUp(navController, viewModel) }
        composable("signIn") { SignIn(navController, viewModel) }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    SupertasterTheme() {
        Profile()
    }
}
