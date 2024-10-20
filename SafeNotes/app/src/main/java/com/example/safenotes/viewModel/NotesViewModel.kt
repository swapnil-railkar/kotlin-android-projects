package com.example.safenotes.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safenotes.data.entity.DefaultCredentials
import com.example.safenotes.data.entity.Note
import com.example.safenotes.data.graph.Graph
import com.example.safenotes.data.repository.DefaultCredentialsRepository
import com.example.safenotes.data.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NotesViewModel(
    private val notesRepository: NotesRepository = Graph.notesRepository,
    private val defaultCredentialsRepository: DefaultCredentialsRepository =
        Graph.defaultCredentialsRepository
): ViewModel() {
    private lateinit var defaultCredentials : Flow<DefaultCredentials>
    private lateinit var notes: Flow<List<Note>>

    init {
        viewModelScope.launch {
            notes = notesRepository.getAllNotes()
            defaultCredentials = defaultCredentialsRepository.getDefaultPassword()
        }
    }


    fun getNotesList() : Flow<List<Note>> {
        return notes
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) { notesRepository.addNote(note) }
    }

    fun deletedNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) { notesRepository.deleteNote(note) }
    }

    fun editNote(title: String, content: String, note: Note) {
        val editedNote = note.copy(
            title = title,
            content = content
        )
        viewModelScope.launch(Dispatchers.IO) { notesRepository.updateNote(editedNote) }
    }

    fun getNotedById(id: Long): Flow<Note> {
        return notesRepository.getNoteById(id)
    }

    fun verifyPasswords(pass: String, confirmPass: String): String? {
        return when {
            pass.isEmpty() -> "Enter Password"
            confirmPass.isEmpty() -> "Confirm Password"
            pass != confirmPass -> "Passwords does not match"
            else -> null
        }
    }

    fun verifyAnswer(question: String, answer: String): String? {
        return when {
            question.isEmpty() -> "Select recovery question"
            answer.isEmpty() -> "Provide answer"
            else -> null
        }
    }

    fun getDefaultCredentials() : Flow<DefaultCredentials> {
        return defaultCredentials
    }

    fun setDefaultCredentials(pass: String, recoveryQuestion: String, answer: String) {
        val credentials = DefaultCredentials(
            password = pass,
            recoveryQuestion = recoveryQuestion,
            answer = answer
        )

        viewModelScope.launch {
            defaultCredentialsRepository.addDefaultCredentials(credentials)
        }
    }

    fun updateDefaultPass(newPass: String, credentials: DefaultCredentials) {
        val updatedCredentials = credentials.copy(
            password = newPass
        )

        viewModelScope.launch {
            defaultCredentialsRepository.updateDefaultCredentials(updatedCredentials)
            notesRepository.updateDefaultPassword(newPass)
        }
    }

    fun updateRecoveryInfo(question: String, answer: String, credentials: DefaultCredentials) {
        val updatedCredentials = credentials.copy(
            recoveryQuestion = question,
            answer = answer
        )
        viewModelScope.launch {
            defaultCredentialsRepository.updateDefaultCredentials(updatedCredentials)
            notesRepository.updateDefaultRecoveryQuestionAnswer(question, answer)
        }
    }
}