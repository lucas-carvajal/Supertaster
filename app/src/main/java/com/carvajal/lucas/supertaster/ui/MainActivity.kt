package com.carvajal.lucas.supertaster.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import com.carvajal.lucas.supertaster.composables.SupertasterApp
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SupertasterTheme {
                SupertasterApp().SupertasterAppScreen()
            }
        }
    }
}


