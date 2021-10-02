package com.example.aliftask.database

import androidx.room.Entity

@Entity(tableName = "cash")
data class Data(
    val endDate: String?,
    val icon: String?,
    val name: String?,
    val url: String?,
)