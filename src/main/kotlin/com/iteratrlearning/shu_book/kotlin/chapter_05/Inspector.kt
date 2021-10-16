package com.iteratrlearning.shu_book.kotlin.chapter_05

class Inspector(vararg conditionalActions: ConditionalAction) {

    private val _conditionalActions = conditionalActions

    fun inspect(facts: Facts): List<Diagnosis> {
        return _conditionalActions.asSequence().map { conditionalAction ->
            val conditionalResult = conditionalAction.evaluate(facts)
            Diagnosis(facts, conditionalAction, conditionalResult)
        }.toList()
    }


}
