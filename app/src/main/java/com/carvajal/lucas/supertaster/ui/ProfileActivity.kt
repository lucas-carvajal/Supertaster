package com.carvajal.lucas.supertaster.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.carvajal.lucas.supertaster.composables.Profile
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme

class ProfileActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SupertasterTheme {
                Profile()
            }
        }
    }

}