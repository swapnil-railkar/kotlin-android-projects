package com.example.safenotes.views.popups

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.safenotes.viewModel.NotesViewModel

@Composable
fun SetDefaultCreds(
    viewModel: NotesViewModel
) {
    val openAlert = remember { mutableStateOf(true) }

    if (openAlert.value) {
        AlertDialog(
            onDismissRequest = { openAlert.value = false },
            buttons = {  },
            title = {
                AlertDialogueContent(
                    viewModel = viewModel,
                    openAlert = openAlert
                )
            }
        )
    }
}

@Composable
private fun AlertDialogueContent(
    viewModel: NotesViewModel,
    openAlert: MutableState<Boolean>
) {
    Column(
        modifier = Modifier.wrapContentSize()
    ) {
        val password = remember { mutableStateOf("") }
        val confirmedPass = remember { mutableStateOf("") }
        var question by remember { mutableStateOf("") }
        var answer by remember { mutableStateOf("") }
        val context = LocalContext.current

        AppDefaultAlertHeader(title = "Set Default Password", modifier = Modifier.padding(2.dp))
        AppDefaultPasswordInput(modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
            password = password)
        AppDefaultPasswordInput(modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
            password = confirmedPass)
        AppDefaultDropDownMenu(onSelectValue = {question = it})
        TextField(value = answer, onValueChange = {answer = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            label = {Text(text = "Answer")})
        AppDefaultButton(title = "Set Password",
            onClick = {
                updateDefaults(password.value, confirmedPass.value, question, answer,
                viewModel, context)
                openAlert.value = false
            },
            modifier = Modifier.fillMaxWidth().padding(2.dp))
    }
}

private fun updateDefaults(
    password: String,
    confirmedPass: String,
    question: String,
    answer: String,
    viewModel: NotesViewModel,
    context: Context
) {
    val errorMsg = verifyCreds(password, confirmedPass, question, answer, viewModel)

    if (errorMsg == null) {
        viewModel.setDefaultCreds(password, question, answer)
    } else {
        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
    }
}
