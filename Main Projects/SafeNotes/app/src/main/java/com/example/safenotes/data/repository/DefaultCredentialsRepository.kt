package com.example.safenotes.data.repository

import com.example.safenotes.data.dao.DefaultCredentialsDao
import com.example.safenotes.data.entity.DefaultCredentials
import kotlinx.coroutines.flow.Flow

class DefaultCredentialsRepository(private val defaultCredentialsDao: DefaultCredentialsDao) {

     fun getDefaultPassword(): Flow<DefaultCredentials> {
        return defaultCredentialsDao.getDefaultCredentials()
    }

    suspend fun updateDefaultCredentials(defaultCredentials: DefaultCredentials) {
        defaultCredentialsDao.updateDefaultCredentials(defaultCredentials)
    }

    suspend fun addDefaultCredentials(defaultCredentials: DefaultCredentials) {
        defaultCredentialsDao.addDefaultCredentials(defaultCredentials)
    }

}