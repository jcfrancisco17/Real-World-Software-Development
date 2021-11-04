package com.iteratrlearning.shu_book.kotlin.chapter_06

class Twoot(val id: String, val senderId: String, val content: String, val position: Position) {

    fun isAfter(otherPosition: Position): Boolean {
        return position.value > otherPosition.value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Twoot

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}
