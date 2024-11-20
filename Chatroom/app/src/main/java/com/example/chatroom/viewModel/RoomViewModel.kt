package com.example.chatroom.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatroom.data.Result
import com.example.chatroom.data.Room
import com.example.chatroom.repository.RoomRepository
import com.example.chatroom.utility.Injection
import kotlinx.coroutines.launch

class RoomViewModel: ViewModel() {

    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> get() = _rooms
    private var roomRepository: RoomRepository = RoomRepository(Injection.instance())

    init {
        loadRooms()
    }

     fun loadRooms() {
        viewModelScope.launch {
            when(val result = roomRepository.getRooms()) {
                is Result.Success -> {
                    _rooms.value = result.data
                }
                is Result.Error -> {

                }
            }
        }
    }

    fun createRoom(title: String) {
        viewModelScope.launch {
            roomRepository.createRoom(title)
        }
    }

}