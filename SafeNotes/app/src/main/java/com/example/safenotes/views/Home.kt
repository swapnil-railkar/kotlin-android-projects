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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.safenotes.R
import com.example.safenotes.data.DummyNotes
import com.example.safenotes.data.Note
import com.example.safenotes.navigation.Screens
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomePage(
    navController: NavController
) {
    val scaffoldState : ScaffoldState = rememberScaffoldState()
    val coroutineScope : CoroutineScope = rememberCoroutineScope()
    val notesList = remember { mutableStateOf(DummyNotes.notesList) }
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
        }
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(notesList.value, key = {note: Note -> note.id}) {
                item: Note ->
                    NoteItem(
                        note = item,
                        onDeleteItem = {
                            //TODO Replace it with delete query
                            notesList.value =
                                notesList.value.filter { note -> note.id != item.id }
                        },
                        onOpenNote = {
                            navController
                                .navigate(Screens.AddEditScreen.route + "/${item.id}")
                        }
                    )
            }
        }
    }
}

@Composable
private fun NoteItem(
    note : Note,
    onDeleteItem : () -> Unit,
    onOpenNote : () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            .clickable { onOpenNote() },
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
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    HomePage(navController = rememberNavController())
}