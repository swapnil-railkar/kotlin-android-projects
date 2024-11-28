package com.example.wishlist.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wishlist.data.dao.WishDao
import com.example.wishlist.data.entity.Wish

@Database(
    entities = [Wish::class],
    version = 1,
    exportSchema = false,
)
abstract class WishDatabase: RoomDatabase() {
    abstract fun wishDao() : WishDao
}