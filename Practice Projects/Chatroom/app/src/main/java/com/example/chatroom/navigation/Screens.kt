package com.example.chatroom.navigation

sealed class Screens(val route: String) {
    data object LoginScreen: Screens("login_screen")
    data object SignUpScreen: Screens("signup_screen")
    data object ChatRoomsScreen:Screens("chatroom_screen")
    data object ChatScreen:Screens("chat_screen")
}