package com.iteratrlearning.shu_book.kotlin.chapter_05

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class BusinessRuleEngineTest : FunSpec({

    lateinit var mockFacts: Facts
    lateinit var mockRule: Rule


    beforeTest {
        mockFacts = mockk()
        mockRule = mockk()
    }

    test("should have no rules initially") {

        val businessRuleEngine = BusinessRuleEngine(mockFacts)

        businessRuleEngine.count() shouldBe 0
    }

    test("should add two rules") {
        val businessRuleEngine = BusinessRuleEngine(mockFacts)

        businessRuleEngine.addRule(mockRule)
        businessRuleEngine.addRule(mockRule)

        businessRuleEngine.count() shouldBe 2
    }

    test("should execute one rule") {
        every { mockRule.perform(any()) } just Runs

        val businessRuleEngine = BusinessRuleEngine(mockFacts)
        businessRuleEngine.addRule(mockRule)
        businessRuleEngine.run()

        verify(exactly = 1) { mockRule.perform(mockFacts) }
    }
})
