package com.iteratrlearning.shu_book.kotlin.chapter_05

class Facts {

    private val facts = mutableMapOf<String, String>()

    fun getFact(name: String) = facts[name]

    fun addFact(name: String, value: String) {
        facts[name] = value
    }
}
