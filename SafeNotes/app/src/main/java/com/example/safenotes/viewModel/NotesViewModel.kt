package com.example.safenotes.viewModel

import androidx.lifecycle.ViewModel
import com.example.safenotes.data.DummyNotes
import com.example.safenotes.data.Note

class NotesViewModel: ViewModel() {
    private var _notesList = DummyNotes.notesList
    private val dummyPass = "abcxyz"
    private val recoveryQuestion = "Favourite Sports club"
    private val answer = "liverpool"

    fun getNotesList() : List<Note> {
        return _notesList
    }

    fun getNote(title: String, content: String): Note {
        return Note(
            id = DummyNotes.notesList.size + 1L,
            title = title,
            content = content,
            recoveryQuestion = recoveryQuestion,
            answer = answer,
            password = dummyPass
        )
    }


    fun addNote(note: Note) {
        _notesList.add(note)
    }

    fun deletedNote(id: Long) {
        _notesList.removeAll {
            it.id == id
        }
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

    // return null if no error else send error msg.
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
}