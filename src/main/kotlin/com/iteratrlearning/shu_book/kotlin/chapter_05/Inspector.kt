package com.iteratrlearning.shu_book.kotlin.chapter_05

class Inspector(private val conditionalActions: Array<ConditionalAction>) {

    fun inspect(facts: Facts): List<Diagnosis> {
        return conditionalActions.asSequence().map { conditionalAction ->
            val conditionalResult = conditionalAction.evaluate(facts)
            Diagnosis(facts, conditionalAction, conditionalResult)
        }.toList()
    }
}
