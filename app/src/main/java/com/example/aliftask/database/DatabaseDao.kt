package com.example.aliftask.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: Data)

}