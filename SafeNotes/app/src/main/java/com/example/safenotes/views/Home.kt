package com.example.safenotes.views

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.safenotes.R
import com.example.safenotes.data.Note
import com.example.safenotes.navigation.Screens
import kotlinx.coroutines.CoroutineScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Divider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.safenotes.viewModel.NotesViewModel
import com.example.safenotes.views.popups.OpenNote
import com.example.safenotes.views.popups.SetDefaultCreds
import kotlinx.coroutines.launch

@Composable
fun HomePage(
    navController: NavController,
    viewModel: NotesViewModel
) {
    val scaffoldState : ScaffoldState = rememberScaffoldState()
    val coroutineScope : CoroutineScope = rememberCoroutineScope()
    val drawerItems = listOf<String>(
        stringResource(id = R.string.set_default_password),
        stringResource(id = R.string.reset_default_password),
        stringResource(id = R.string.reset_recovery_question)
    )
    val context = LocalContext.current
    var drawerItemToOpen by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            NotesAppBar(
                title = stringResource(id = R.string.app_name),
                state = scaffoldState,
                scope = coroutineScope
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.AddEditScreen.route + "/0L")
                },
                containerColor = colorResource(id = R.color.app_default_color)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add note",
                    tint = Color.White
                )
            }
        },
        scaffoldState = scaffoldState,
        drawerContent = {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(start = 4.dp, top = 8.dp)
            )
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(drawerItems) {
                    item: String ->
                    DrawerItem(
                        title = item,
                        onClickTitle = {
                            drawerItemToOpen = item
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                    )
                }
            }
        }
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(viewModel.getNotesList(), key = {note: Note -> note.id}) {
                item: Note ->
                    NoteItem(
                        note = item,
                        onDeleteItem = {
                            viewModel.deletedNote(item.id)
                        },
                        navController = navController
                    )
            }
        }

        when(drawerItemToOpen) {
            stringResource(id = R.string.set_default_password) ->
                SetDefaultCreds(viewModel = viewModel)
            else -> Toast.makeText(context, "Not implemented yet", Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
private fun NoteItem(
    note : Note,
    onDeleteItem : () -> Unit,
    navController: NavController
) {
    val openNotePopUp = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            .clickable { openNotePopUp.value = true },
        elevation = 10.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = note.title, fontWeight = FontWeight.ExtraBold)
            IconButton(onClick = { onDeleteItem() }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete note")
            }

        }
    }

    if (openNotePopUp.value) {
        OpenNote(note = note, navController = navController, openNotePopUp)
    }
}

@Composable
private fun DrawerItem(
    title: String,
    onClickTitle: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClickTitle() }
    ) {
        Text(text = title)
        Divider(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp))
    }
}