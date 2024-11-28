package com.example.safenotes.views.popups

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.safenotes.data.entity.Note
import com.example.safenotes.navigation.Screens

/**
 *  This functions verifies the credentials given by user and open note if they're correct.
 *  Hierarchy of components :
 *  > Alert
 *      > Header
 *      > Text field (input password)
 *      > Button (enter)
 *      > Forget Password ?
 *          > Alert
 *              > Header
 *              > Text (recovery question)
 *              > Text field (answer)
 *              > Button (enter)
 */
@Composable
fun OpenNote(
    note : Note,
    navController : NavController,
    openDialog: MutableState<Boolean>
) {
    // Initially user will be prompted with password alert.
    // If user chooses to verify credentials via recovery question then this triggered is used to
    // open recovery question - answer alert
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
        AppDefaultAlertHeader(title = "Enter Password")

        Text(text = note.recoveryQuestion)
        Spacer(modifier = Modifier.padding(top = 8.dp))


        TextField(
            value = answer.value,
            onValueChange = {
                answer.value = it
            },
            label = { Text(text = "Answer")}
        )
        Spacer(modifier = Modifier.padding(top = 8.dp))

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
            }
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
        AppDefaultAlertHeader(title = "Enter Password")
        AppDefaultPasswordInput(password = password, placeHolder = "Password")
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
            }
        )
        AppDefaultTextButton(
            title = "Forgot Password",
            onClick = { openQuestionDialogue.value = true }
        )
    }
}