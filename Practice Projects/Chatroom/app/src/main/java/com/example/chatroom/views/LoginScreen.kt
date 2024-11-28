package com.example.chatroom.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.chatroom.data.Result
import com.example.chatroom.viewModel.AuthViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onNavigateToSignUpScreen: () -> Unit,
    onSignInSuccess: () -> Unit
) {
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    val result by authViewModel.authResult.observeAsState()

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = email,
            onValueChange = {email = it},
            label = { Text(text = "Email")},
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(value = password,
            onValueChange = {password = it},
            label = { Text(text = "Password")},
            modifier = Modifier.padding(8.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                authViewModel.logIn(email, password)
                when(result) {
                    is Result.Error -> {

                    }
                    is Result.Success -> {
                        onSignInSuccess()
                    }

                    else -> {

                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Log In")
        }
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Do not have an account?")
            Text(text = " Sign in", color = Color.Cyan,
                modifier = Modifier.clickable { onNavigateToSignUpScreen() })
        }
    }

}