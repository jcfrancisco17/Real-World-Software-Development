package com.iteratrlearning.shu_book.kotlin.chapter_03

/**
 * This is a [Kotlin functional interface](https://kotlinlang.org/docs/fun-interfaces.html). It uses the `fun` modifier.
 */
fun interface BankTransactionFilter {
    fun test(bankTransaction: BankTransaction): Boolean
}