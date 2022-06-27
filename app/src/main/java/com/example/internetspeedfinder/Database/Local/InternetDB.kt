package com.example.internetspeedfinder.Database.Local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.internetspeedfinder.Database.Local.Constants.DbConstants
import com.example.internetspeedfinder.Database.Local.Intefaces.InternetDAO

@Database(entities = [InternetTable::class],version = 1)
abstract class InternetDB : RoomDatabase() {
abstract val internetDAOQueries :InternetDAO

    companion object{
        @Volatile
        private var INSTANCE : InternetDB? = null

        fun getInstance(context: Context) :InternetDB{
            synchronized(this){
                var instance : InternetDB? = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        InternetDB::class.java,
                        "${DbConstants.internet_Database_name}"
                    ).build()
                }
                return instance

            }
        }
    }
}