package com.iteratrlearning.shu_book.kotlin.chapter_05

interface ConditionalAction {
    fun perform(facts: Facts)
    fun evaluate(facts: Facts): Boolean
}
