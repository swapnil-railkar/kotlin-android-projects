package com.example.safenotes.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.safenotes.data.entity.DefaultCredentials
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DefaultCredentialsDao {

    @Insert
    abstract suspend fun addDefaultCredentials(defaultCredentials: DefaultCredentials)

    @Update
    abstract suspend fun updateDefaultCredentials(defaultCredentials: DefaultCredentials)

    @Query(value = "select * from `default_credentials` limit 1")
    abstract fun getDefaultCredentials(): Flow<DefaultCredentials>

}