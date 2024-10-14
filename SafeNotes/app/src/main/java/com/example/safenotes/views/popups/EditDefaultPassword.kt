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
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.safenotes.viewModel.NotesViewModel

@Composable
fun EditDefaultPasswordAlert(
    viewModel: NotesViewModel,
) {
    val openAlert = remember{ mutableStateOf(true) }
    
    if (openAlert.value) {
        AlertDialog(
            onDismissRequest = { openAlert.value = false },
            buttons = {},
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
private fun AlertViewContent(viewModel: NotesViewModel) {
    val newPass = remember { mutableStateOf("") }
    val confirmNewPass = remember { mutableStateOf("") }
    val answer = remember { mutableStateOf("") }
    val question = viewModel.getDefaultCreds()!!.recoveryQuestion
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppDefaultAlertHeader(title = "Reset Default Password")

        AppDefaultPasswordInput(password = newPass, placeHolder = "Password")

        AppDefaultPasswordInput(password = confirmNewPass, placeHolder = "Confirm Password")

        Text(text = question, modifier = Modifier.wrapContentSize())

        TextField(
            value = answer.value,
            onValueChange = {answer.value = it},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(top = 8.dp))

        AppDefaultButton(
            title = "Reset Password",
            onClick = {
                verifyAndUpdatePassword(viewModel, newPass.value, confirmNewPass.value,
                    answer.value, context)
            }
        )
    }
}

private fun verifyAndUpdatePassword(
    viewModel: NotesViewModel,
    newPass: String,
    confirmNewPass: String,
    answer: String,
    context: Context
) {
    val passErrors = viewModel.verifyPasswords(newPass, confirmNewPass)

    if (passErrors == null) {
        if (answer.lowercase().trim() == viewModel.getDefaultCreds()!!.answer.lowercase().trim()) {
            viewModel.updateDefaultPass(newPass)
            Toast.makeText(context, "Default password updated", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Answer is not correct", Toast.LENGTH_LONG).show()
        }
    } else {
        Toast.makeText(context, passErrors, Toast.LENGTH_LONG).show()
    }
}

@Preview(showBackground = true)
@Composable
private fun EditDefaultPasswordAlertPreview() {
    val viewModel : NotesViewModel = viewModel()
    viewModel.setDefaultCreds("1234","abc","xyz")
    EditDefaultPasswordAlert(viewModel = viewModel)
}
