package com.example.safenotes.views.popups

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.safenotes.R
import com.example.safenotes.viewModel.NotesViewModel

@Composable
fun ResetRecoveryQuestionAlert(
    viewModel: NotesViewModel
) {
    val openAlert = remember { mutableStateOf(true) }
    
    if (openAlert.value) {
        AlertDialog(
            onDismissRequest = { openAlert.value = false },
            buttons = { },
            title = {},
            text = {
                AlertViewContent(
                    viewModel = viewModel
                )
            }
        )
    }
}

@Composable
private fun AlertViewContent(
    viewModel: NotesViewModel
) {
    val password = remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    var question by remember { mutableStateOf("") }
    val context = LocalContext.current
    
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(2.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppDefaultAlertHeader(title = "Reset Recovery Question")
        AppDefaultPasswordInput(password = password, placeHolder = "Password")
        AppDefaultDropDownMenu(onSelectValue = {question = it})
        TextField(value = answer, onValueChange = {answer = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            colors = TextFieldDefaults
                .textFieldColors(cursorColor = colorResource(id = R.color.app_default_color))
        )
        Spacer(modifier = Modifier.padding(top = 8.dp))
        AppDefaultButton(
            title = "Reset Recovery Question",
            onClick = {
                verifyAndUpdateQuestion(viewModel, question, answer, password.value, context)
            }
        )
    }
}

fun verifyAndUpdateQuestion(
    viewModel: NotesViewModel,
    question: String,
    answer: String,
    password: String,
    context: Context
) {
    val errors = viewModel.verifyAnswer(question, answer)

    if (errors == null) {
        if (password == viewModel.getDefaultCreds()!!.password) {
            viewModel.updateRecoveryInfo(question, answer)
            Toast.makeText(context, "Recovery question updated", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Incorrect password", Toast.LENGTH_LONG).show()
        }
    } else {
        Toast.makeText(context, errors, Toast.LENGTH_LONG).show()
    }
}

@Preview(showBackground = true)
@Composable
private fun ResetRecoveryQuestionAlertPreview() {
    val viewModel : NotesViewModel = viewModel()
    viewModel.setDefaultCreds("1234","abc","xyz")
    ResetRecoveryQuestionAlert(viewModel = viewModel)
}
