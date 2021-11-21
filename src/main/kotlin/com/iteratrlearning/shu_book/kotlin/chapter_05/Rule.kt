package com.iteratrlearning.shu_book.kotlin.chapter_05

class Rule(private val condition: Condition, val action: Action) {

    fun perform(facts: Facts) {
        if (condition.evaluate(facts)) {
            action.perform(facts)
        }
    }
}
