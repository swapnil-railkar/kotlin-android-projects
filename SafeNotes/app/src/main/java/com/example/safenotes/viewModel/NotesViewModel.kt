package com.example.safenotes.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.safenotes.data.DefaultCredentials
import com.example.safenotes.data.Note

class NotesViewModel: ViewModel() {
    private var _notesList : MutableList<Note> = mutableListOf()
    private var dummyPass = "abcxyz"
    private var recoveryQuestion = "Favourite Sports club"
    private var answer = "liverpool"
    private var defaultCreds : MutableState<DefaultCredentials> = mutableStateOf(
        DefaultCredentials(
            id = -1L,
            password = "",
            recoveryQuestion = "",
            answer = ""
        )
    )


    fun getNotesList() : List<Note> {
        return _notesList
    }

    fun getNote(title: String, content: String, usesDefaultPass: Boolean): Note {
        if (usesDefaultPass) {
            dummyPass = defaultCreds.value.password
            recoveryQuestion = defaultCreds.value.recoveryQuestion
            answer = defaultCreds.value.answer
        }
        return Note(
            id = _notesList.size + 1L,
            title = title,
            content = content,
            recoveryQuestion = recoveryQuestion,
            answer = answer,
            password = dummyPass,
            usesDefaults = usesDefaultPass
        )
    }


    fun addNote(note: Note) {
        _notesList.add(note)
    }

    fun deletedNote(id: Long) {
        _notesList = _notesList.filter { it.id != id }.toMutableList()
    }

    fun editNote(id: Long, title: String, content: String, note: Note) {
        val editedNote = note.copy(
            title = title,
            content = content
        )
        _notesList = _notesList.map {
            if (it.id == id) {
                editedNote
            } else{
                it
            }
        }.toMutableList()
    }

    fun getNotedById(id: Long): Note? {
        return when{
            id != 0L -> _notesList.first {
                it.id == id
            }
            else -> null
        }
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

    fun getDefaultCreds() : DefaultCredentials? {
        return if (defaultCreds.value.id == -1L) {
            null
        } else {
            defaultCreds.value
        }
    }

    fun setDefaultCreds(pass: String, recoveryQuestion: String, answer: String) {
        defaultCreds.value = DefaultCredentials(
            password = pass,
            recoveryQuestion = recoveryQuestion,
            answer = answer
        )
    }

    fun updateDefaultPass(newPass: String) {
        defaultCreds.value = defaultCreds.value.copy(
            password = newPass
        )
        _notesList.filter {
            it.usesDefaults
        }.map {
            it.password = newPass
        }
    }

    fun updateRecoveryInfo(question: String, answer: String) {
        defaultCreds.value = defaultCreds.value.copy(
            recoveryQuestion = question,
            answer = answer
        )
        _notesList.filter {
            it.usesDefaults
        }.map {
            it.recoveryQuestion = question
            it.answer = answer
        }
    }
}