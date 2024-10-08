package com.example.safenotes.views.popups

import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.safenotes.R

@Composable
fun PasswordInput(
    modifier: Modifier,
    password: MutableState<String>
) {
    val passVisible = remember { mutableStateOf(false) }
    TextField(
        modifier = modifier,
        value = password.value,
        singleLine = true,
        placeholder = { Text(text = "Password") },
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
}

@Composable
fun AppDefaultTextButton(
    title : String,
    onClick : () -> Unit,
    modifier: Modifier
) {
    TextButton(
        modifier = modifier,
        onClick = { onClick() }
    ) {
        Text(
            text = title,
            color = colorResource(id = R.color.app_default_color)
        )
    }
}

@Composable
fun AppDefaultAlertHeader(
    title : String,
    modifier: Modifier
) {
    Text(
        text = title,
        fontWeight = FontWeight.ExtraBold,
        modifier = modifier
    )
}

@Composable
fun AppDefaultButton(
    title : String,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.app_default_color)
        )
    ) {
        Text(text = title, color = Color.White)
    }
}