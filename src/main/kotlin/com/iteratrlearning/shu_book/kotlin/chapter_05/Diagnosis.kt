package com.iteratrlearning.shu_book.kotlin.chapter_05

class Diagnosis(
    val facts: Facts,
    private val conditionalAction: ConditionalAction,
    val isPositive: Boolean
) {

    override fun toString(): String {
        return "Report{conditionalAction=$conditionalAction, facts=$facts, result=$isPositive}"
    }

}
