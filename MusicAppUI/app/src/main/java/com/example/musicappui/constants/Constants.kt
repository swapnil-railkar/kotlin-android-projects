package com.example.musicappui.constants

import com.example.musicappui.R

data class Constants (
    val categories: List<String> =
        listOf<String>("Hits", "Happy", "Workout", "Running", "TGIF", "Yoga"),
    val grouped: Map<Char, List<String>> =
        listOf<String>("New Releases", "Favourite", "Top Rated").groupBy { it[0] },
    val libraryItems: List<LibraryItem> =
        listOf(
            LibraryItem(
                title = "Playlist",
                drawable = R.drawable.baseline_playlist_play_24
            ),
            LibraryItem(
                title = "Artist",
                drawable = R.drawable.baseline_person_pin_24
            ),
            LibraryItem(
                title = "Songs",
                drawable = R.drawable.baseline_headphones_24
            ),
            LibraryItem(
                title = "Genre",
                drawable = R.drawable.baseline_format_list_bulleted_24
            )
        )
)

data class LibraryItem(
    val title: String,
    val drawable: Int
)