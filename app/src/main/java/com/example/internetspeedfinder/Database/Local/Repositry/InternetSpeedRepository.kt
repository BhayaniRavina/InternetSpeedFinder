package com.example.internetspeedfinder.Database.Local.Repositry

import com.example.internetspeedfinder.Database.Local.Intefaces.InternetDAO
import com.example.internetspeedfinder.Database.Local.InternetTable

class InternetSpeedRepository(private val dao : InternetDAO) {
    val allRecords = dao.getAllRecords()
    val minimumSpeed = dao .getMinimumSpeedRecord()
    val maximumSpeed = dao.getMaximumSpeedRecord()
    val meanSpeed = dao.getMeanSpeedRecord()

    suspend fun insert(internetTable: InternetTable){
        dao.insertInternetSpeedData(internetTable)
    }
}