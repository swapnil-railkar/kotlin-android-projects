package com.example.safenotes.data.graph

import android.content.Context
import androidx.room.Room
import com.example.safenotes.data.database.MainDatabase
import com.example.safenotes.data.repository.DefaultCredentialsRepository
import com.example.safenotes.data.repository.NotesRepository

object Graph {
    lateinit var database: MainDatabase

    val notesRepository by lazy { NotesRepository(database.NoteDao()) }
    val defaultCredentialsRepository by lazy {
        DefaultCredentialsRepository(database.DefaultCredentialsDao())
    }

    fun provide(context: Context) {
        database = Room
            .databaseBuilder(context, MainDatabase::class.java, "main_db")
            .build()
    }
}