package com.example.safenotes.views

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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.safenotes.R
import com.example.safenotes.data.entity.Note
import com.example.safenotes.navigation.Screens
import com.example.safenotes.viewModel.NotesViewModel
import com.example.safenotes.views.popups.OpenNote

/**
 *  Landing page of application. Hierarchy of components :
 *  > Top Bar
 *  > Search field
 *  > Notes
 *      > Title
 *      > Delete
 *  > FAB
 */
@Composable
fun HomePage(
    navController: NavController,
    viewModel: NotesViewModel
) {
    val scaffoldState : ScaffoldState = rememberScaffoldState()
    var searchText by remember { mutableStateOf("") }
    //Initially search filter is empty so it will fetch all notes.
    val notesList = viewModel.fetchNotes(searchText).collectAsState(initial = listOf())

    Scaffold(
        topBar = {
            Column {
                // Top Bar
                NotesAppBar(
                    title = stringResource(id = R.string.app_name),
                    viewModel = viewModel
                )
                // Search field
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { search ->
                        searchText = search
                    },
                    label = { Text(text = "Search")},
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search Note")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    colors = TextFieldDefaults
                        .textFieldColors(cursorColor = colorResource(id = R.color.app_default_color)),
                    singleLine = true
                )
            }
        },
        floatingActionButton = {
            // FAB
            FloatingActionButton(
                onClick = {
                    // Home > FAB is used to add new note, hence passed -1L as argument.
                    // (note id starts from 0L)
                    navController.navigate(Screens.AddEditScreen.route + "/-1L")
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
        scaffoldState = scaffoldState
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            items(items = notesList.value, key = {note: Note -> note.id}) {
                // Note
                item: Note ->
                    NoteItem(
                        note = item,
                        onDeleteItem = {
                            viewModel.deletedNote(item)
                        },
                        navController = navController
                    )
            }
        }
    }
}

@Composable
private fun NoteItem(
    note : Note,
    onDeleteItem : () -> Unit,
    navController: NavController
) {
    // trigger to open popup to open note
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
            // Title of note
            Text(text = note.title, fontWeight = FontWeight.ExtraBold)

            // Delete note button
            IconButton(onClick = { onDeleteItem() }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete note")
            }

        }
    }

    if (openNotePopUp.value) {
        OpenNote(note = note, navController = navController, openNotePopUp)
    }
}