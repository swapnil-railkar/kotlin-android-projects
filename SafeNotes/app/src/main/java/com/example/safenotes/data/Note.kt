package com.example.safenotes.data

data class Note(
    val id : Long = 0L,
    val title : String,
    val content : String,
    val password : String,
    val recoveryQuestion : String,
    val answer : String,
    val usesDefaults : Boolean = false
)

object DummyNotes {
    val notesList = listOf<Note>(
        Note(
            id = 1L,
            title = "Dummy Title 1",
            password = "1234",
            recoveryQuestion = "Favourite Sports club",
            answer = "Liverpool",
            content = "Prow scuttle parrel provost Sail ho shrouds spirits boom mizzenmast yardarm."
                    + " Pinnace holystone mizzenmast quarter crow's nest nipperkin grog yardarm"
                    + " hempen halter furl. Swab barque interloper chantey doubloon starboard"
                    + " grog black jack gangway rutters."
        ),
        Note(
            id = 2L,
            title = "Dummy Title 2",
            password = "4567",
            recoveryQuestion = "Favourite Sports club",
            answer = "Liverpool",
            content = "Deadlights jack lad schooner scallywag dance the hempen jig carouser"
                    + " broadside cable strike colors. Bring a spring upon her cable holystone"
                    + " blow the man down spanker."
        ),
        Note(
            id = 3L,
            title = "Dummy Title 3",
            password = "8901",
            recoveryQuestion = "Favourite Sports club",
            answer = "Liverpool",
            content = "Shiver me timbers to go on account lookout wherry doubloon chase. "
                    + "Belay yo-ho-ho keelhaul squiffy black spot yardarm spyglass sheet"
                    + " transom heave to."
        ),
        Note(
            id = 4L,
            title = "Dummy Title 4",
            password = "2345",
            recoveryQuestion = "Favourite Sports club",
            answer = "Liverpool",
            content = "Trysail Sail ho Corsair red ensign hulk smartly boom jib rum gangway."
                    + " Case shot Shiver me timbers gangplank crack Jennys tea cup ballast Blimey"
                    + " lee snow crow's nest rutters. Fluke jib scourge of the seven seas"
                    + " boatswain schooner gaff booty Jack Tar transom spirits."
        )
    )
}
