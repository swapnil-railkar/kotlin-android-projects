package com.example.musicappui.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicappui.MainViewModel
import com.example.musicappui.navigation.Navigation
import com.example.musicappui.navigation.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainView() {
    val scope : CoroutineScope = rememberCoroutineScope()
    val scaffoldState : ScaffoldState = rememberScaffoldState()

    val viewModel : MainViewModel = viewModel()
    val screen by remember { mutableStateOf(viewModel.currentScreen.value) }

    val title = remember { mutableStateOf(screen.title) }
    val drawerScreens = listOf<Screens.DrawerScreen>(
        Screens.DrawerScreen.Account,
        Screens.DrawerScreen.AddAccount,
        Screens.DrawerScreen.Subscription
    )

    val controller = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    val isDialogueOpen = remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBarUI(title = title.value, scaffoldState = scaffoldState, scope = scope)},
        scaffoldState = scaffoldState,
        drawerContent = {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(drawerScreens) {
                    item ->
                    DrawerItem(
                    selected = currentRoute == item.route,
                    onDrawerItemClicked = {scope.launch {
                        scaffoldState.drawerState.close()
                        if (item.route == Screens.DrawerScreen.AddAccount.route) {
                            // open dialogue.
                            isDialogueOpen.value = true
                        } else {
                            title.value = item.title
                        }
                    }},
                    item = item
                    )
                }
            }
        },
        bottomBar = { BottomAppBarUI(currentRoute = screen.route, navController = controller)}

    ) {
        Navigation(navController = controller, viewModel = viewModel, paddingValues = it)
    }

}