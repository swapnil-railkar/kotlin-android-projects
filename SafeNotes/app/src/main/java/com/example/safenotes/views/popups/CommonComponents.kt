package com.example.safenotes.views.popups

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.safenotes.R
import com.example.safenotes.data.DefaultCreds

@Composable
fun AppDefaultPasswordInput(
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppDefaultDropDownMenu(
    onSelectValue: (String) -> Unit
) {
    var selectedOption by remember { mutableStateOf("Select recovery question") }
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.wrapContentSize().padding(4.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {expanded = !expanded}
        ) {
            TextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)}
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ){
                DefaultCreds.recoveryQuestions.forEach {
                    option: String ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = {
                            expanded = false
                            selectedOption = option
                            onSelectValue(option)
                        }
                    )
                }
            }
        }
    }
}

