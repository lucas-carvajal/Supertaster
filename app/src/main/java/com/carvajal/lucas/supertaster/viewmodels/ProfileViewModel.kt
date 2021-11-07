package com.carvajal.lucas.supertaster.viewmodels

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel(private val mAuth: FirebaseAuth): ViewModel() {

    var isLoggedIn = mAuth.currentUser != null

    fun signUpUser(email: String, password: String, isSuccessful: () -> Unit, isNotSuccessful: () -> Unit) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    isLoggedIn = true
                    isSuccessful()
                } else {
                    isNotSuccessful()
                }
            }
    }

    fun signInUser(email: String, password: String, isSuccessful: () -> Unit, isNotSuccessful: () -> Unit) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() {task ->
                if (task.isSuccessful) {
                    isLoggedIn = true
                    isSuccessful()
                } else {
                    isNotSuccessful()
                }
            }
    }

    fun signOutUser() {
        mAuth.signOut()
        isLoggedIn = false
    }


}