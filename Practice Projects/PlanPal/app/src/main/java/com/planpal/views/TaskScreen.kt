package com.planpal.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TaskScreenView() {

    Scaffold(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.padding(it)) {

        }
    }
}