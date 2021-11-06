package com.carvajal.lucas.supertaster.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Profile(mAuth: FirebaseAuth) {

}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    SupertasterTheme() {
        Profile(FirebaseAuth.getInstance())
    }
}
