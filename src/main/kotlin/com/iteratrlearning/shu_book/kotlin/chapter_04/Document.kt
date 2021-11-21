package com.iteratrlearning.shu_book.kotlin.chapter_04

class Document(private val attributes: Map<String, String>) {

    fun getAttribute(attributeName: String): String? {
        return attributes[attributeName]
    }

}
