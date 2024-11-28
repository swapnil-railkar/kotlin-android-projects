package com.example.wishlist.views

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.wishlist.R
import com.example.wishlist.WishViewModel
import com.example.wishlist.data.entity.DummyWish
import com.example.wishlist.data.entity.Wish
import com.example.wishlist.navigation.Screens

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            AppBarView(title = stringResource(id = R.string.app_name)) {
                Toast.makeText(context, "Back button is clicked", Toast.LENGTH_LONG).show()
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screens.AddScreen.route + "/0L") },
                contentColor = Color.White,
                backgroundColor = colorResource(id = R.color.app_bar_color),
                modifier = Modifier.padding(all = 4.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        val wishList = viewModel.getAllWishes().collectAsState(initial = listOf())
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            items(wishList.value, key = {wish -> wish.id}) {
                item ->

                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart
                            || it == DismissValue.DismissedToEnd) {
                            viewModel.deleteWish(item)
                        }
                        true
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    background = {},
                    directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                    dismissThresholds = {FractionalThreshold(0.25F)},
                    dismissContent = {
                        WishItem(wish = item) {
                            navController.navigate(Screens.AddScreen.route + "/${item.id}")
                        }
                    }
                )

            }
        }
    }
}
