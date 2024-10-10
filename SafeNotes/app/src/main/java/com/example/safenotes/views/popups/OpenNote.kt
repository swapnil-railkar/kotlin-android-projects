package com.example.safenotes.views.popups

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.safenotes.data.DummyNotes
import com.example.safenotes.data.Note
import com.example.safenotes.navigation.Screens

@Composable
fun OpenNote(
    note : Note,
    navController : NavController,
    openDialog: MutableState<Boolean>
) {
    val openQuestionDialogue = remember { mutableStateOf(false) }
    val context = LocalContext.current
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            buttons = {},
            text = {
                AlertDialogContent(
                    note = note,
                    navController = navController,
                    openQuestionDialogue = openQuestionDialogue,
                    context = context
                )
            },

        )
    }
    
    if (openQuestionDialogue.value) {
        AlertDialog(
            onDismissRequest = { openQuestionDialogue.value = false },
            buttons = {},
            text = {
                QuestionDialogueContent(
                    note = note,
                    navController = navController,
                    context = context
                )
            }
        )
    }

}

@Composable
private fun QuestionDialogueContent(
    note: Note,
    navController: NavController,
    context : Context
) {
    val answer = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppDefaultAlertHeader(title = "Enter Password", modifier = Modifier.padding(8.dp))
        Text(text = note.recoveryQuestion)
        TextField(
            value = answer.value,
            onValueChange = {
                answer.value = it
            },
            label = { Text(text = "Answer")}
        )
        AppDefaultButton(
            title = "Enter",
            onClick = {
                if (answer.value.trim().lowercase() == note.answer.trim().lowercase()) {
                    navController.navigate(Screens.AddEditScreen.route + "/${note.id}")
                } else {
                    Toast
                        .makeText(context, "Wrong answer, please try again",
                            Toast.LENGTH_LONG).show()
                    answer.value = ""
                }
            },
            modifier = Modifier.fillMaxWidth().padding(2.dp)
        )
    }
}


@Composable
private fun AlertDialogContent(
    note: Note,
    navController: NavController,
    openQuestionDialogue: MutableState<Boolean>,
    context: Context
) {
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppDefaultAlertHeader(title = "Enter Password", modifier = Modifier.padding(8.dp))
        AppDefaultPasswordInput(modifier = Modifier.fillMaxWidth(), password = password)
        AppDefaultButton(
            title = "Enter",
            onClick = {
                if (password.value == note.password) {
                    navController.navigate(Screens.AddEditScreen.route + "/${note.id}")
                } else {
                    Toast
                        .makeText(context, "Incorrect password, please try again",
                            Toast.LENGTH_LONG).show()
                    password.value = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp, top = 8.dp)
        )
        AppDefaultTextButton(
            title = "Forgot Password",
            onClick = { openQuestionDialogue.value = true },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OpenNotePopUpPreview() {
    val openDialog = remember {
        mutableStateOf(true)
    }
    OpenNote(note = DummyNotes.notesList[0], rememberNavController(), openDialog)
}