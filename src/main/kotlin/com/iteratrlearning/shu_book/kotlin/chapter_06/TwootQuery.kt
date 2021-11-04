package com.iteratrlearning.shu_book.kotlin.chapter_06

class TwootQuery {

    var inUsers = mutableSetOf<String>()
        private set
    lateinit var lastSeenPosition: Position
        private set

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
