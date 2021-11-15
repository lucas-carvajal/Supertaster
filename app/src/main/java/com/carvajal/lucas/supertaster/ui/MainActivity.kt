package com.carvajal.lucas.supertaster.ui

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import com.carvajal.lucas.supertaster.composables.REQUEST_IMAGE_CAPTURE
import com.carvajal.lucas.supertaster.composables.SupertasterApp
import com.carvajal.lucas.supertaster.composables.addRecipeImage
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        setContent {
            SupertasterTheme {
                SupertasterApp().SupertasterAppScreen()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        var currentUser = mAuth.currentUser

        if (currentUser != null) {
            //TODO download new data if available
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            addRecipeImage(imageBitmap)
        }
    }
}


