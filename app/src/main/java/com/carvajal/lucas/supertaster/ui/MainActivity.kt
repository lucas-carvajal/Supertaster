package com.carvajal.lucas.supertaster.ui

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.carvajal.lucas.supertaster.composables.REQUEST_IMAGE_CAPTURE
import com.carvajal.lucas.supertaster.composables.SupertasterApp
import com.carvajal.lucas.supertaster.composables.addRecipeImage
import com.carvajal.lucas.supertaster.data.AppDatabase
import com.carvajal.lucas.supertaster.data.AppRepository
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme
import com.carvajal.lucas.supertaster.viewmodels.*
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        val repository = initRepository()

        val dashboardViewModel: DashboardViewModel = ViewModelProvider(this, DashboardViewModelFactory(repository))
            .get(DashboardViewModel::class.java)
        val cookbookViewModel: CookbookViewModel = ViewModelProvider(this, CookbookViewModelFactory(repository))
            .get(CookbookViewModel::class.java)
        val addViewModel: AddViewModel = ViewModelProvider(this, AddViewModelFactory(repository))
            .get(AddViewModel::class.java)

        setContent {
            SupertasterTheme {
                SupertasterApp().SupertasterAppScreen(
                    dashboardViewModel = dashboardViewModel,
                    cookbookViewModel = cookbookViewModel,
                    addViewModel = addViewModel,
                    repository = repository,
                    mainContext = this
                )
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

    private fun initRepository(): AppRepository {
        val recipeDao = AppDatabase.getInstance(applicationContext).recipeDao()
        val recipeImageDao = AppDatabase.getInstance(applicationContext).recipeImageDao()
        val recipeIngredientDao = AppDatabase.getInstance(applicationContext).recipeIngredientDao()
        val recipeStepDao = AppDatabase.getInstance(applicationContext).recipeStepDao()
        val cookbookDao = AppDatabase.getInstance(applicationContext).cookbookDao()
        val cookbookRecipeDao = AppDatabase.getInstance(applicationContext).cookbookRecipeDao()

        return AppRepository(
            recipeDao,
            recipeImageDao,
            recipeIngredientDao,
            recipeStepDao,
            cookbookDao,
            cookbookRecipeDao
        )
    }
}


