package com.iteratrlearning.shu_book.kotlin.chapter_03

fun interface BankTransactionSummarizer {
    fun summarize(accumulator: Double, bankTransaction: BankTransaction): Double
}