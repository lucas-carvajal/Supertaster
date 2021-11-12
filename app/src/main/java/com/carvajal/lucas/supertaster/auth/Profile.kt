package com.carvajal.lucas.supertaster.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme
import com.carvajal.lucas.supertaster.viewmodels.ProfileViewModel

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
