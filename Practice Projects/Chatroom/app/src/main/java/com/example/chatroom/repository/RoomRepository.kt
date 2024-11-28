package com.example.chatroom.repository

import com.example.chatroom.data.Result
import com.example.chatroom.data.Room
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RoomRepository(
    private val fireStore: FirebaseFirestore
) {
    suspend fun createRoom(title: String): Result<Unit> {
        return try {
            val room = Room(title = title)
            fireStore.collection("rooms").add(room).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getRooms(): Result<List<Room>> {
        return try {
            val querySnapshot = fireStore.collection("rooms").get().await()
            val rooms = querySnapshot.documents.map { 
                document ->  document.toObject(Room::class.java)!!.copy(id = document.id)
            }
            Result.Success(rooms)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}