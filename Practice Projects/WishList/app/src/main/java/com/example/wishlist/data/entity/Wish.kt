package com.example.wishlist.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,

    @ColumnInfo(name = "title")
    val title : String,

    @ColumnInfo(name = "description")
    val description : String
)

object DummyWish {
    val wishList = listOf<Wish>(
        Wish(1L,
            "Dummy Title 1",
            "Prow scuttle parrel provost Sail ho shrouds spirits boom mizzenmast yardarm"),
        Wish(2L,
            "Dummy Title 2",
            "Deadlights jack lad schooner scallywag dance the hempen jig carouser" +
                    " broadside cable strike colors."),
        Wish(3L,
            "Dummy Title 3",
            "Bring a spring upon her cable holystone blow the man down spanker Shiver" +
                    " me timbers to go on account lookout wherry doubloon chase." ),
        Wish(4L,
            "Dummy Title 4",
            "Belay yo-ho-ho keelhaul squiffy black spot yardarm spyglass" +
                    " sheet transom heave to." ),
    )
}