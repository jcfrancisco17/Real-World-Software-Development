package com.iteratrlearning.shu_book.kotlin.chapter_05

class BusinessRuleEngine(val facts: Facts) {

    private val rules = mutableListOf<Rule>()

    fun count() = rules.size

    fun addRule(rule: Rule) {
        rules.add(rule)
    }

    fun run() {
        rules.forEach { rule -> rule.perform(facts) }
    }

}
