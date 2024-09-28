package com.example.wishlist.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.wishlist.data.entity.Wish
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WishDao {

    @Insert
    abstract suspend fun addWish(wish: Wish)

    @Update
    abstract suspend fun updateWish(wish: Wish)

    @Query("select * from `wish`")
    abstract fun getAllWishes() : Flow<List<Wish>>

    @Query("select * from `wish` where id = :id")
    abstract fun getWishById(id : Long) : Flow<Wish>

    @Delete
    abstract suspend fun deleteWish(wish: Wish)
}