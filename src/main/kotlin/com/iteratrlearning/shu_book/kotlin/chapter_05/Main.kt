package com.iteratrlearning.shu_book.kotlin.chapter_05

fun main() {

    val env = Facts()
    env.addFact("name", "Bob")
    env.addFact("jobTitle", "CEO")

    val businessRuleEngine = BusinessRuleEngine(env)

    val ruleSendEmailToSalesWhenCEO = RuleBuilder.`when` { facts -> "CEO" == facts.getFact("jobTitle") }.then { facts ->
        run { // needs run to be use this block
            val name = facts.getFact("name")
            println("Relevant Customer!!! $name")
        }
    }

    businessRuleEngine.addRule(ruleSendEmailToSalesWhenCEO)
    businessRuleEngine.run()
}
