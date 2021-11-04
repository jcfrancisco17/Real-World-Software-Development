package com.iteratrlearning.shu_book.kotlin.chapter_06.in_memory

import com.iteratrlearning.shu_book.kotlin.chapter_06.Position
import com.iteratrlearning.shu_book.kotlin.chapter_06.Twoot
import com.iteratrlearning.shu_book.kotlin.chapter_06.TwootQuery
import com.iteratrlearning.shu_book.kotlin.chapter_06.TwootRepository
import java.util.function.Consumer

class InMemoryTwootRepository : TwootRepository {
    val twoots = mutableListOf<Twoot>()
    var currentPosition = Position.INITIAL_POSITION

    override fun add(id: String, userId: String, content: String): Twoot {
        currentPosition = currentPosition.next()

        val twootPosition = currentPosition
        val twoot = Twoot(id, userId, content, twootPosition)
        twoots.add(twoot)
        return twoot
    }

    override fun get(id: String): Twoot? {
        return twoots.firstOrNull { twoot -> twoot.id == id }
    }

    override fun delete(twoot: Twoot) {
        twoots.remove(twoot)
    }

    override fun query(twootQuery: TwootQuery, callback: Consumer<Twoot>) {
        if (!twootQuery.hasUsers()) {
            return
        }

        val lastSeenPosition = twootQuery.lastSeenPosition
        val inUsers = twootQuery.inUsers

        twoots
            .filter { twoot -> inUsers.contains(twoot.senderId) }
            .filter { twoot -> twoot.isAfter(lastSeenPosition) }
            .forEach(callback)
    }

    override fun clear() {
//        TODO("Not yet implemented")
    }

}
