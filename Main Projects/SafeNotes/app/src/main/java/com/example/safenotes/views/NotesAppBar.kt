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
import androidx.compose.runtime.collectAsState
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
import com.example.safenotes.views.popups.SetDefaultCredentials

/**
 *  This function defines top bar for application.
 *  Hierarchy of components :
 *  >Top App Bar
 *      > Navigation Icon (more-vert, on tap expands to three options)
 *          > Set Default Password
 *          > Reset Default Password
 *          > Reset Recovery Question
 *
 *  Note : more-vert is three vertical dotted lines component
 */
@Composable
fun NotesAppBar(
    title : String,
    viewModel: NotesViewModel
) {
    val context = LocalContext.current
    // trigger to expand more-vert
    var expanded by remember { mutableStateOf(false) }
    val defaultCredentials = viewModel.getDefaultCredentials().collectAsState(initial = null)
    val menuItems = listOf<String>(
        stringResource(id = R.string.set_default_password),
        stringResource(id = R.string.reset_default_password),
        stringResource(id = R.string.reset_recovery_question)
    )
    // variable used to store value of item clicked in more-vert
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
        // when user clicks 'Set Default Password' then check whether default credentials are
        // configured. If not configured then allow user to add credentials else show toast.
        stringResource(id = R.string.set_default_password) -> {
            if (defaultCredentials.value == null) {
                SetDefaultCredentials(viewModel = viewModel)
            } else {
                Toast.makeText(context, "Default credentials are already configured",
                    Toast.LENGTH_LONG).show()
            }
        }

        // when user clicks 'Reset Default Password' or 'Reset Recovery Question'
        // then check whether default credentials are configured.
        // If configured then allow user to update credentials else show toast.
        stringResource(id = R.string.reset_default_password) ->
            if (defaultCredentials.value == null) {
                Toast.makeText(context, "Default credentials are not configured",
                    Toast.LENGTH_LONG).show() 
            } else {
                EditDefaultPasswordAlert(
                    viewModel = viewModel,
                    defaultCredentials = defaultCredentials.value!!
                )
            }
        stringResource(id = R.string.reset_recovery_question) ->
            if (defaultCredentials.value == null) {
                Toast.makeText(context, "Default credentials are not configured",
                    Toast.LENGTH_LONG).show()
            } else {
                ResetRecoveryQuestionAlert(
                    viewModel = viewModel,
                    defaultCredentials = defaultCredentials.value!!
                )
            }
    }
}