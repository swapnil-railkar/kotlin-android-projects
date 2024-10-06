package com.example.safenotes.views

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import com.example.safenotes.R
import kotlinx.coroutines.launch

@Composable
fun NotesAppBar(
    title : String,
    state : ScaffoldState,
    scope : CoroutineScope
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (title == stringResource(id = R.string.app_name)) {
                IconButton(
                    onClick = { scope.launch {state.drawerState.open()}}
                ) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Setting Icon")
                }
            }
        },
        backgroundColor = colorResource(id = R.color.app_default_color),
        contentColor = Color.White
    )
}

@Preview(showBackground = true)
@Composable
fun NotesAppBarPreview() {
    NotesAppBar(title = stringResource(
        id = R.string.add_note),
        state = rememberScaffoldState(),
        scope = rememberCoroutineScope()
    )
}