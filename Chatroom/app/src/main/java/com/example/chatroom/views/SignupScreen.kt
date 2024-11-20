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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.chatroom.viewModel.AuthViewModel

@Composable
fun SignupScreen(
    authViewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit
){
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        
        OutlinedTextField(value = firstName,
            onValueChange = {firstName = it},
            label = { Text(text = "First Name")},
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(value = lastName,
            onValueChange = {lastName = it},
            label = { Text(text = "Last Name")},
            modifier = Modifier.padding(8.dp)
        )
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
                authViewModel.signUp(firstName, lastName, email, password)
                firstName =  ""
                lastName = ""
                email= ""
                password= ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sign In")
        }

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Already have an account?")
            Text(text = " Log in", color = Color.Cyan,
                modifier = Modifier.clickable { onNavigateToLogin() })

        }
    }
}