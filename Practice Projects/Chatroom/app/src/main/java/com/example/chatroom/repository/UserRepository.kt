package com.example.chatroom.repository

import com.example.chatroom.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.example.chatroom.data.Result

class UserRepository(private val auth: FirebaseAuth,
                     private val firestore: FirebaseFirestore) {
    suspend fun signUp(firstName:String, lastName:String, email: String, password: String):
            Result<Boolean> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val user = User(firstName, lastName, email)
            saveUserToFirebase(user)
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun logIn(email: String, password: String): Result<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private suspend fun saveUserToFirebase(user: User) {
        firestore.collection("users").document(user.email).set(user).await()
    }
}