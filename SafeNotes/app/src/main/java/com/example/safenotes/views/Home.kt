package com.example.safenotes.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun HomePage(
    navController: NavController,
    viewModel: NotesViewModel
) {
    val scaffoldState : ScaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = {
            NotesAppBar(
                title = stringResource(id = R.string.app_name),
                viewModel = viewModel
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
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
        val items = viewModel.getNotesList().collectAsState(initial = listOf())
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            items(items.value, key = {note: Note -> note.id}) {
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