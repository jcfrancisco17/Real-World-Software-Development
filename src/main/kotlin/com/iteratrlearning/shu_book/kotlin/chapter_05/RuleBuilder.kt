package com.iteratrlearning.shu_book.kotlin.chapter_05

class RuleBuilder private constructor(private val condition: Condition) {

    companion object {
        fun `when`(condition: Condition): RuleBuilder { // there are back ticks in when because when is a keyword in kotlin
            return RuleBuilder(condition)
        }
    }

    fun then(action: Action): Rule {
        return Rule(condition, action)
    }

}
