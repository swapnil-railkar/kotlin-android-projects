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
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.safenotes.R
import com.example.safenotes.data.entity.DefaultCredentials
import com.example.safenotes.viewModel.NotesViewModel

/**
 *  This function defines Reset Default Password Popup
 *  Hierarchy of components
 *      > Title (Reset Default Password)
 *      > Enter new password
 *      > Confirm new password
 *      > Recovery question
 *      > Answer
 *      > Confirmation Button
 *
 *  Note : Password will not be edited if answer is wrong.
 */
@Composable
fun EditDefaultPasswordAlert(
    viewModel: NotesViewModel,
    defaultCredentials: DefaultCredentials
) {
    val openAlert = remember{ mutableStateOf(true) }
    
    if (openAlert.value) {
        AlertDialog(
            onDismissRequest = { openAlert.value = false },
            buttons = {},
            title = {},
            text = {
                AlertViewContent(
                    viewModel = viewModel,
                    defaultCredentials = defaultCredentials
                )
            }
        )
    }
    
}

@Composable
private fun AlertViewContent(viewModel: NotesViewModel, defaultCredentials: DefaultCredentials) {
    val newPass = remember { mutableStateOf("") }
    val confirmNewPass = remember { mutableStateOf("") }
    val answer = remember { mutableStateOf("") }
    val question = defaultCredentials.recoveryQuestion
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
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults
                .textFieldColors(cursorColor = colorResource(id = R.color.app_default_color))
        )

        Spacer(modifier = Modifier.padding(top = 8.dp))

        AppDefaultButton(
            title = "Reset Password",
            onClick = {
                verifyAndUpdatePassword(defaultCredentials,viewModel, newPass.value,
                    confirmNewPass.value, answer.value, context)
            }
        )
    }
}

private fun verifyAndUpdatePassword(
    defaultCredentials: DefaultCredentials,
    viewModel: NotesViewModel,
    newPass: String,
    confirmNewPass: String,
    answer: String,
    context: Context
) {
    val passErrors = viewModel.verifyPasswords(newPass, confirmNewPass)

    if (passErrors == null) {
        if (answer.lowercase().trim() == defaultCredentials.answer.lowercase().trim()) {
            viewModel.updateDefaultPass(newPass, defaultCredentials)
            Toast.makeText(context, "Default password updated", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Answer is not correct", Toast.LENGTH_LONG).show()
        }
    } else {
        Toast.makeText(context, passErrors, Toast.LENGTH_LONG).show()
    }
}
