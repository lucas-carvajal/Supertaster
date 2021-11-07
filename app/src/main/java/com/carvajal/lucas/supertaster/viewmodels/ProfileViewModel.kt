package com.carvajal.lucas.supertaster.viewmodels

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel(mAuth: FirebaseAuth): ViewModel() {

    val mAuth = mAuth

    private val _isLoggedIn = MutableLiveData<Boolean>(mAuth.currentUser != null)
    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    fun signUpUser(email: String, password: String, isSuccessful: () -> Unit, isNotSuccessful: () -> Unit) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    _isLoggedIn.value = true
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
                    _isLoggedIn.value = true
                    isSuccessful()
                } else {
                    isNotSuccessful()
                }
            }
    }

    fun signOutUser() {
        mAuth.signOut()
        _isLoggedIn.value = false
    }


}