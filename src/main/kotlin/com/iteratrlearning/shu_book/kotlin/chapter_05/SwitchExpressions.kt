package com.iteratrlearning.shu_book.kotlin.chapter_05


fun main() {
    val dealStage = Stage.LEAD
    val amount = 10

    when (dealStage) {
        Stage.LEAD -> 0.2
        Stage.EVALUATING -> 0.5
        Stage.INTERESTED -> 0.8
        Stage.CLOSED -> 1
    }.let { it.toDouble() * amount }.let { println(it) }

}
