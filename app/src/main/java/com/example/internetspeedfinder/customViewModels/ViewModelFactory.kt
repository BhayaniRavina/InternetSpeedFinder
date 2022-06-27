package com.example.internetspeedfinder.customViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.internetspeedfinder.Database.Local.Repositry.InternetSpeedRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: InternetSpeedRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
            return MainActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}