package com.example.safenotes.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.safenotes.R
import com.example.safenotes.viewModel.NotesViewModel
import com.example.safenotes.views.popups.SaveNote

@Composable
fun AddEditScreen(
    id : Long,
    viewModel: NotesViewModel,
    navController: NavController
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val openSaveNotePopup = remember{ mutableStateOf(false) }
    val selectedNote = viewModel.getNotedById(id)
    title = selectedNote?.title ?: stringResource(id = R.string.add_note)
    content = selectedNote?.content ?: ""
    
    Scaffold(
        topBar = {
            NotesAppBar(title = title, viewModel = viewModel)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (selectedNote == null) {
                       openSaveNotePopup.value = true
                    } else {
                        viewModel.editNote(id, title, content, selectedNote)
                    }

                },
                containerColor = colorResource(id = R.color.app_default_color)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save note",
                    tint = Color.White
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            // to input title
            OutlinedTextField(
                value = title,
                onValueChange = {
                    titleValue -> title = titleValue
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 16.dp),
                label = { Text(text = "Title") }
            )

            // to input description
            OutlinedTextField(
                value = content,
                onValueChange = {
                        descriptionValue -> content = descriptionValue
                },
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 16.dp)
                    .fillMaxSize(),
                label = { Text(text = "Description")}
            )
        }

        if (openSaveNotePopup.value) {
            SaveNote(
                title = title,
                content = content,
                viewModel = viewModel,
                navController = navController
            )
        }


    }
}