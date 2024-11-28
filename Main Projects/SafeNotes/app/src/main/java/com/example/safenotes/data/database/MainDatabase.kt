package com.example.safenotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.safenotes.data.dao.DefaultCredentialsDao
import com.example.safenotes.data.dao.NoteDao
import com.example.safenotes.data.entity.DefaultCredentials
import com.example.safenotes.data.entity.Note

@Database(
    entities = [Note::class, DefaultCredentials::class],
    version = 1,
    exportSchema = false
)
abstract class MainDatabase: RoomDatabase() {

    abstract fun NoteDao() : NoteDao
    
    abstract fun DefaultCredentialsDao() : DefaultCredentialsDao

}