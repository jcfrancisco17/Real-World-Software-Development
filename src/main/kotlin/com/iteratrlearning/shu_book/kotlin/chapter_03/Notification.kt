package com.iteratrlearning.shu_book.kotlin.chapter_03

class Notification {
    private val errors: MutableList<String> = mutableListOf()

    fun addError(message: String) {
        errors.add(message)
    }

    fun hasErrors(): Boolean {
        return errors.isNotEmpty()
    }

    fun errorMessage(): String {
        return errors.toString()
    }

    fun getErrors(): List<String> {
        return ArrayList(errors)
    }


}