package com.example.safenotes.views.popups

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.safenotes.data.DefaultCreds
import com.example.safenotes.data.Note
import com.example.safenotes.navigation.Screens
import com.example.safenotes.viewModel.NotesViewModel

@Composable
fun SaveNote(
    title: String,
    content: String,
    viewModel: NotesViewModel,
    navController: NavController
) {
    val openDialog = remember{ mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            buttons = {},
            text = {
                NoteCredentialsAlert(
                    title = title,
                    content = content,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        )
    }
}

@Composable
private fun NoteCredentialsAlert(
    title: String,
    content: String,
    viewModel: NotesViewModel,
    navController: NavController
) {
    var selectedQuestion by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val password = remember { mutableStateOf("") }
        val conformPassword = remember { mutableStateOf("") }

        AppDefaultAlertHeader(title = "Set Password", modifier = Modifier.padding(2.dp))
        AppDefaultPasswordInput(modifier = Modifier.padding(4.dp), password = password)
        AppDefaultPasswordInput(modifier = Modifier.padding(4.dp), password = conformPassword)
        AppDefaultDropDownMenu(
            onSelectValue = {
                selectedQuestion = it
            }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = answer,
            onValueChange = {
                answer = it
            }
        )

        AppDefaultButton(
            title = "Save Note",
            onClick = {
                addAuthToNote(
                    note = viewModel.getNote(title, content),
                    password = password.value,
                    conformPassword = conformPassword.value,
                    question = selectedQuestion,
                    answer = answer,
                    viewModel = viewModel,
                    context = context,
                    navController = navController
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        AppDefaultTextButton(
            title = "Use Default Password",
            onClick = {
                addAuthToNote(
                    note = viewModel.getNote(title, content),
                    password = DefaultCreds.credentials.password,
                    conformPassword = DefaultCreds.credentials.password,
                    question = DefaultCreds.credentials.recoveryQuestion,
                    answer = DefaultCreds.credentials.answer,
                    viewModel = viewModel,
                    context = context,
                    navController = navController
                )
            },
            modifier = Modifier.fillMaxWidth().padding(2.dp)
        )

    }

}

fun addAuthToNote(
    note: Note,
    password: String,
    conformPassword: String,
    question: String,
    answer: String,
    viewModel: NotesViewModel,
    context: Context,
    navController: NavController
){
    val passErrors = viewModel.verifyPasswords(password, conformPassword)
    val answerErrors = viewModel.verifyAnswer(question, answer)

    val errorMsg : String? = when {
        passErrors != null -> passErrors
        answerErrors != null -> answerErrors
        else -> null
    }

    if (errorMsg == null) {
        val finalNote = note.copy(
            password = password,
            recoveryQuestion = question,
            answer = answer
        )
        viewModel.addNote(finalNote)
        Toast.makeText(context, "Note Saved", Toast.LENGTH_LONG).show()
        navController.navigate(Screens.HomeScreen.route)
    } else {
        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
    }
}