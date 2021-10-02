package com.example.aliftask.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cash")
data class Data(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val endDate: String?,
    val icon: String?,
    val name: String?,
    val url: String?,
)