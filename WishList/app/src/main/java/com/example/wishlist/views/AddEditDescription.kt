package com.example.wishlist.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlist.R
import com.example.wishlist.WishViewModel
import com.example.wishlist.data.entity.Wish
import com.example.wishlist.navigation.Screens
import kotlinx.coroutines.launch

@Composable
fun AddEditDescription(
    id : Long,
    viewModel: WishViewModel,
    navController : NavController
) {
    var snackBarMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    if (id != 0L) {
        val wish  = viewModel
            .getWishById(id)
            .collectAsState(initial = Wish(0L, "", ""))
        viewModel.wishTitle = wish.value.title
        viewModel.wishDescription = wish.value.description
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarView(
                title =
                    if (id != 0L) stringResource(id = R.string.update_wish)
                    else stringResource(id = R.string.add_wish)
            ) {navController.navigateUp()}
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            
            WishTextField(
                value = viewModel.wishTitle,
                label = "Title",
                onValueChange = {
                    title -> viewModel.updateWishTitle(title)
                }
            )

            Spacer(modifier = Modifier.padding(10.dp))

            WishTextField(
                value = viewModel.wishDescription,
                label = "Description",
                onValueChange = {
                        description -> viewModel.updateWishDescription(description)
                }
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {
                    if (viewModel.wishTitle.isNotEmpty()
                        && viewModel.wishDescription.isNotEmpty()) {
                        if (id != 0L) {
                         // Update wish
                            viewModel.updateWish(
                                Wish(
                                    id = id,
                                    title = viewModel.wishTitle,
                                    description = viewModel.wishDescription
                                )
                            )
                        } else {
                            // Add wish
                            viewModel.addWish(
                                Wish(
                                    title = viewModel.wishTitle.trim(),
                                    description =  viewModel.wishDescription.trim()
                                )
                            )
                            snackBarMessage = "Wish has been created"
                        }
                    } else {
                        snackBarMessage = "Enter fields to create wish"
                    }
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(snackBarMessage)
                        navController.navigateUp()
                    }
                },
                colors = ButtonDefaults
                    .buttonColors(containerColor = colorResource(id = R.color.app_bar_color))
            ) {
                Text(text =
                if (id != 0L) stringResource(id = R.string.update_wish)
                else stringResource(id = R.string.add_wish),
                    color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishTextField(
    value : String,
    label : String,
    onValueChange : (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, color = colorResource(id = R.color.app_bar_color))},
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.app_bar_color) 
        )
    )
}