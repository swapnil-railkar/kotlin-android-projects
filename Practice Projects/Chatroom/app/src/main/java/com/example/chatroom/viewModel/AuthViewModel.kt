package com.example.chatroom.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatroom.data.Result
import com.example.chatroom.repository.UserRepository
import com.example.chatroom.utility.Injection
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    private val userRepository: UserRepository
    private val _authResult = MutableLiveData<Result<Boolean>>()
    val authResult: LiveData<Result<Boolean>> = _authResult

    init {
        userRepository = UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
    }

    fun signUp(firstName: String, lastName: String, email: String, password: String){
        viewModelScope.launch {
            _authResult.value = userRepository.signUp(firstName, lastName, email, password)
        }
    }

    fun logIn(email: String, password: String) {
        viewModelScope.launch {
            _authResult.value = userRepository.logIn(email, password)
        }
    }
}