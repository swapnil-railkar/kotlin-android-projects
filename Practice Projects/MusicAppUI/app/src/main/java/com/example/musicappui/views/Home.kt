package com.example.musicappui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicappui.R
import com.example.musicappui.constants.Constants

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home() {

    LazyColumn{
        Constants().grouped.forEach{
            stickyHeader { 
                Text(text = it.value[0], modifier = Modifier.padding(16.dp))
                LazyRow{
                    items(Constants().categories) {
                        category -> BrowseItem(category, R.drawable.baseline_audio_file_24)
                    }
                }
            }
        }
    }
}
