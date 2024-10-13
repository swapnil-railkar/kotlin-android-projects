package com.example.safenotes.views

import android.widget.Toast
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.safenotes.R
import com.example.safenotes.viewModel.NotesViewModel
import com.example.safenotes.views.popups.EditDefaultPasswordAlert
import com.example.safenotes.views.popups.ResetRecoveryQuestionAlert
import com.example.safenotes.views.popups.SetDefaultCreds

@Composable
fun NotesAppBar(
    title : String,
    viewModel: NotesViewModel
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val menuItems = listOf<String>(
        stringResource(id = R.string.set_default_password),
        stringResource(id = R.string.reset_default_password),
        stringResource(id = R.string.reset_recovery_question)
    )
    var menuItemToOpen by remember { mutableStateOf("") }
    
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(
                onClick = { expanded = true}
            ) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                menuItems.forEach{
                    item: String ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = { menuItemToOpen = item }
                    )
                }
            }
        },
        backgroundColor = colorResource(id = R.color.app_default_color),
        contentColor = Color.White
    )

    when(menuItemToOpen) {
        stringResource(id = R.string.set_default_password) -> {
            if (viewModel.getDefaultCreds() == null) {
                SetDefaultCreds(viewModel = viewModel)
            } else {
                Toast.makeText(context, "Default credentials are already configured",
                    Toast.LENGTH_LONG).show()
            }
        }
        stringResource(id = R.string.reset_default_password) ->
            if (viewModel.getDefaultCreds() == null) {
                Toast.makeText(context, "Default credentials are not configured",
                    Toast.LENGTH_LONG).show() 
            } else {
                EditDefaultPasswordAlert(viewModel = viewModel)
            }
        stringResource(id = R.string.reset_recovery_question) ->
            if (viewModel.getDefaultCreds() == null) {
                Toast.makeText(context, "Default credentials are not configured",
                    Toast.LENGTH_LONG).show()
            } else {
                ResetRecoveryQuestionAlert(viewModel = viewModel)
            }
    }
}