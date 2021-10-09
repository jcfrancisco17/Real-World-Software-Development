package com.iteratrlearning.shu_book.kotlin.chapter_04

// Using Exception does not pose a compiler problem
// Probably because Kotlin does not have a concept  of checked exceptions.
class UnknownFileTypeException(message: String?) : RuntimeException(message)
