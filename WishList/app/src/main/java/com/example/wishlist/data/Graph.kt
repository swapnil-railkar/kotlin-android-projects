package com.example.wishlist.data

import android.content.Context
import androidx.room.Room
import com.example.wishlist.data.database.WishDatabase
import com.example.wishlist.data.repository.WishRepository

object Graph {
    lateinit var database: WishDatabase

    val wishRepository by lazy {
        WishRepository(database.wishDao())
    }

    fun provide(context: Context) {
        database = Room
            .databaseBuilder(context, WishDatabase::class.java, "wishlist.db")
            .build()
    }
}