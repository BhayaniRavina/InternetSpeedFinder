package com.example.internetspeedfinder.Database.Local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.internetspeedfinder.Database.Local.Constants.DbConstants

@Entity(tableName = DbConstants.internet_table_name)
data class InternetTable(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "${DbConstants.id_column}")
        val id: Int,

        @ColumnInfo(name = "${DbConstants.speed_column}")
        val speed : Double
    ){}