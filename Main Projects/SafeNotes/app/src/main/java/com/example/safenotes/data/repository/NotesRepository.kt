package com.example.safenotes.data.repository

import com.example.safenotes.data.dao.NoteDao
import com.example.safenotes.data.entity.Note
import kotlinx.coroutines.flow.Flow

class NotesRepository(private val notesDao: NoteDao) {

    suspend fun addNote(note: Note) {
        notesDao.addNote(note = note)
    }

    suspend fun updateNote(note: Note) {
        notesDao.updateNote(note = note)
    }

    suspend fun deleteNote(note: Note) {
        notesDao.deleteNote(note)
    }

    fun getAllNotes(): Flow<List<Note>> {
        return notesDao.getAllNotes()
    }

    fun getNoteById(id: Long): Flow<Note> {
        return notesDao.getNoteById(id)
    }

    suspend fun updateDefaultPassword(password: String) {
        notesDao.updateDefaultPassword(password)
    }

    suspend fun updateDefaultRecoveryQuestionAnswer(question: String, answer: String) {
        notesDao.updateDefaultRecoveryQuestionAnswer(question, answer)
    }

    fun searchNoteByTitle(title: String): Flow<List<Note>> {
        val arg = "%$title%"
        return notesDao.getNoteByTitleLike(arg)
    }
}