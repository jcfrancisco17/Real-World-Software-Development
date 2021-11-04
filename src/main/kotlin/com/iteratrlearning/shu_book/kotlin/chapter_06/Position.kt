package com.iteratrlearning.shu_book.kotlin.chapter_06

class Position(val value: Int) {

    companion object {
        val INITIAL_POSITION = Position(1)
    }

    fun next() : Position {
        return Position(value + 1)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Position

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value
    }

    override fun toString(): String {
        return "Position(value=$value)"
    }


}
