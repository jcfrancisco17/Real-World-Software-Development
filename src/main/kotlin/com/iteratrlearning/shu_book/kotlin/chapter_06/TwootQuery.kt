package com.iteratrlearning.shu_book.kotlin.chapter_06

class TwootQuery {

    val inUsers = mutableSetOf<String>()
    lateinit var lastSeenPosition: Position

    fun inUsers(inUsers: Set<String>): TwootQuery {
        this.inUsers.addAll(inUsers)
        return this
    }

    fun inUsers(vararg inUsers: String): TwootQuery {
        return inUsers(setOf(*inUsers))
    }

    fun lastSeenPosition(lastSeenPosition: Position): TwootQuery {
        this.lastSeenPosition = lastSeenPosition
        return this
    }

    fun hasUsers(): Boolean {
        return inUsers.isNotEmpty()
    }


}
