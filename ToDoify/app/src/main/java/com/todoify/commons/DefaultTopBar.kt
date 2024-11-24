package com.todoify.commons

import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.todoify.R

@Composable
fun DefaultTopBar(
    topBarContent: @Composable () -> Unit,
    onClearAllItems: () -> Unit
) {
    TopAppBar(
        title = { topBarContent() },
        navigationIcon = {
            TopBarMoreVert {
                onClearAllItems()
            }
        },
        backgroundColor = colorResource(id = R.color.app_default_color)
    )
}