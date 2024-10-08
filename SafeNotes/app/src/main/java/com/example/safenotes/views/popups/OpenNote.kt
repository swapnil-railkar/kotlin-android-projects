package com.example.safenotes.views.popups

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.safenotes.data.DummyNotes
import com.example.safenotes.data.Note
import com.example.safenotes.navigation.Screens
import com.example.safenotes.R

@Composable
fun OpenNote(
    note : Note,
    navController : NavController,
    openDialog: MutableState<Boolean>
) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            buttons = {},
            text = {
                AlertDialogContent(note = note, navController = navController)
            },

        )
    }

}


@Composable
private fun AlertDialogContent(
    note: Note,
    navController: NavController
) {
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current
    val passVisible = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter Password",
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(8.dp)
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password.value,
            singleLine = true,
            placeholder = { Text(text = "Password")},
            onValueChange = {
                password.value = it
            },
            visualTransformation = if (passVisible.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passVisible.value) 
                    painterResource(id = R.drawable.baseline_visibility_24)
                else painterResource(id = R.drawable.baseline_visibility_off_24)
                IconButton(onClick = { passVisible.value = !passVisible.value }) {
                    Icon(painter = image, contentDescription = "show password")
                }
            }
        )
        Button(
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
                .padding(start = 4.dp, end = 4.dp, top = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.app_default_color)
            )
        ) {
            Text(text = "Enter", color = Color.White)
        }
        TextButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = "Forget Password",
                color = colorResource(id = R.color.app_default_color)
            )
        }
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