package com.iteratrlearning.shu_book.kotlin.chapter_05

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize

class InspectorTest : FunSpec({

    test("inspect one condition evaluates to true") {
        val facts = Facts()
        facts.addFact("jobTitle", "CEO")

        val jobTitleCondition = JobTitleCondition()

        val inspector = Inspector(arrayOf(jobTitleCondition))
        val diagnosis: List<Diagnosis> = inspector.inspect(facts)

        diagnosis shouldHaveSize 1
        diagnosis[0].isPositive
    }
})


private class JobTitleCondition : ConditionalAction {
    override fun perform(facts: Facts) {
        TODO("Not yet implemented")
    }

    override fun evaluate(facts: Facts): Boolean = "CEO" == facts.getFact("jobTitle")
}
