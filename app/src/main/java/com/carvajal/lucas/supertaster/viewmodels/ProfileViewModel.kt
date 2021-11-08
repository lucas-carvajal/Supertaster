package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel(): ViewModel() {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

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