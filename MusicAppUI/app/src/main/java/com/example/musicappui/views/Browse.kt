package com.example.musicappui.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import com.example.musicappui.R
import com.example.musicappui.constants.Constants

@Composable
fun Browse() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(Constants().categories) {
            category -> BrowseItem(category = category, drawable = R.drawable.baseline_audio_file_24)
        }
    }
}
