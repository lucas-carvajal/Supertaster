package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.carvajal.lucas.supertaster.data.AppRepository

class CookbookViewModelFactory(private val repository: AppRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CookbookViewModel::class.java)) {
            return CookbookViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}