package com.example.chatroom.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatroom.data.Room
import com.example.chatroom.viewModel.RoomViewModel

@Composable
fun ChatRoomScreen(
    roomViewModel: RoomViewModel = viewModel()
) {
    val rooms by roomViewModel.rooms.observeAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(text = "Chat Rooms", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn{
            items(rooms) {
                item: Room ->  RoomItem(title = item.title) {
                    // join room
                }
            }
        }

        Button(onClick = { showDialog = true }) {
            Text(text = "Create Room")
        }

        if (showDialog) {
            OpenCreateRoomDialogue(
                closeDialog = { showDialog = it },
                roomViewModel = roomViewModel
            )
        }
    }
}

@Composable
private fun OpenCreateRoomDialogue(
    roomViewModel: RoomViewModel,
    closeDialog: (Boolean) -> Unit
) {
    var title by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { closeDialog(false) },
        confirmButton = { },
        title = { Text(text = "Create New Room")},
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                TextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        roomViewModel.createRoom(title)
                        closeDialog(true)
                    }) {
                        Text(text = "Confirm")
                    }

                    Button(onClick = { closeDialog(false) }) {
                        Text(text = "Cancel")
                    }
                }

            }
        }
    )
}

@Composable
private fun RoomItem(
    title: String,
    onJoinRoom: () -> Unit
) {
    Row( 
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Normal)
        OutlinedButton(onClick = { onJoinRoom() }) {
            Text(text = "Join")
        }
    }
}
