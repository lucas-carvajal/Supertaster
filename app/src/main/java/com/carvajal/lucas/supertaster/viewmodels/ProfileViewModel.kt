package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel(mAuth: FirebaseAuth): ViewModel() {

    val mAuth = mAuth

    fun isLoggedIn(): Boolean {
        return mAuth.currentUser != null
    }

    fun signOut() {
        //TODO sign out
    }

    fun signIn() {
        //TODO sign in
    }

}