package com.example.internetspeedfinder.customViewModels

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internetspeedfinder.Database.Local.InternetTable
import com.example.internetspeedfinder.Database.Local.Repositry.InternetSpeedRepository
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: InternetSpeedRepository) : ViewModel(), Observable {
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    val getAllData = repository.allRecords
    val getMinSpeed = repository.minimumSpeed
    val getMaximumSpeed = repository.maximumSpeed
    val getMeanSpeed = repository.meanSpeed

    @Bindable
    val currentRecord = MutableLiveData<String>()
    @Bindable
    val meanRecord = MutableLiveData<String>()
    @Bindable
    val maxRecord = MutableLiveData<String>()
    @Bindable
    val minRecord = MutableLiveData<String>()

    /*init {

        meanRecord.value = getMeanSpeed.toString()
        maxRecord.value = getMaximumSpeed.toString()
        minRecord.value = getMinSpeed.toString()
    }*/

    /*fun SaveData(){
        //val speed : Double = (currentRecord.value)!!.toDouble()
        //insert(InternetTable(0,speed))
    }*/

    fun insert(internateTable: InternetTable) =
        viewModelScope.launch {
            repository.insert(internateTable)
        }
}