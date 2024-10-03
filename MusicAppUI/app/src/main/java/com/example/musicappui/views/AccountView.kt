package com.example.musicappui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.musicappui.R
import androidx.compose.ui.unit.dp

@Composable
fun AccountView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Icon(imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
                Column {
                    Text(text = "My Name")
                    Text(text = "myname@email.com")
                }
            }
            
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
        }

        Row(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.baseline_queue_music_24),
                contentDescription = null,
                modifier = Modifier.padding(16.dp))
            Text(text = "My music", modifier = Modifier.padding(8.dp))
        }
    }
}