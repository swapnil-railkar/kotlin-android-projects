package com.example.wishlist.views

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wishlist.R

@Composable
fun AppBarView(
    title : String,
    onBackClick : () -> Unit
) {
    TopAppBar(
        title = {
             Text(text = title,
                 color = Color.White,
                 modifier = Modifier
                     .padding(start = 4.dp)
                     .heightIn(max = 24.dp)
             )
        },
        elevation = 4.dp,
        backgroundColor = colorResource(id = R.color.app_bar_color),
        navigationIcon = {
            if (!title.contains(stringResource(id = R.string.app_name))) {
                BackButton(onBackClick)
            }

        }
    )
}

@Composable
fun BackButton(onBackClick : () -> Unit) {
    IconButton(
        onClick = { onBackClick() }
    ) {
        Icon(imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back button",
            tint = Color.White
        )
    }
}