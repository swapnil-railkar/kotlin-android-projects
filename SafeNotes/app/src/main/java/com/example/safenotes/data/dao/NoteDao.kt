package com.example.safenotes.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.safenotes.data.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NoteDao {

    @Insert
    abstract suspend fun addNote(note: Note)

    @Delete
    abstract suspend fun deleteNote(note: Note)

    @Update
    abstract suspend fun updateNote(note: Note)

    @Query(value = "select * from `note`")
    abstract fun getAllNotes() : Flow<List<Note>>

    @Query(value = "select * from `note` where `id` = :id")
    abstract fun getNoteById(id: Long) : Flow<Note>

    @Query(value = "update `note` set `password` = :password where `usesDefaults` = true")
    abstract suspend fun updateDefaultPassword(password: String)

    @Query(
        value = "update `note` set `recoveryQuestion` = :question, `answer` = :answer"
            + " where `usesDefaults` = true")
    abstract suspend fun updateDefaultRecoveryQuestionAnswer(question: String, answer: String)
}