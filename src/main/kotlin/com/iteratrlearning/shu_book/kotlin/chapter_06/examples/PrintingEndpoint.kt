package com.iteratrlearning.shu_book.kotlin.chapter_06.examples

import com.iteratrlearning.shu_book.kotlin.chapter_06.ReceiverEndpoint
import com.iteratrlearning.shu_book.kotlin.chapter_06.Twoot

class PrintingEndpoint : ReceiverEndpoint {
    override fun onTwoot(twoot: Twoot) {
        println("${twoot.senderId}: ${twoot.content}")
    }
}
