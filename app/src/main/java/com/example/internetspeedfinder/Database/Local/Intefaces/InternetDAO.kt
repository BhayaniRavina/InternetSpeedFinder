package com.example.internetspeedfinder.Database.Local.Intefaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.internetspeedfinder.Database.Local.Constants.DbConstants
import com.example.internetspeedfinder.Database.Local.InternetTable

@Dao
interface InternetDAO {
    @Insert
    suspend fun insertInternetSpeedData(insertTable: InternetTable) : Long

    @Query("SELECT * FROM ${DbConstants.internet_table_name} order by id desc")
    fun getAllRecords() :  LiveData<List<InternetTable>>

    //@Query("SELECT * FROM ${DbConstants.internet_table_name} where speed IN(select AVG(speed) FROM ${DbConstants.internet_table_name})")
    @Query("SELECT id, AVG(speed) as speed FROM ${DbConstants.internet_table_name}")
    fun getMeanSpeedRecord() : LiveData<List<InternetTable>>

    @Query("SELECT * FROM ${DbConstants.internet_table_name} where speed IN(select MAX(speed) FROM ${DbConstants.internet_table_name})")
    fun getMaximumSpeedRecord() : LiveData<List<InternetTable>>

    @Query("SELECT * FROM ${DbConstants.internet_table_name} where speed IN(select MIN(speed) FROM ${DbConstants.internet_table_name})")
    fun getMinimumSpeedRecord() : LiveData<List<InternetTable>>
}