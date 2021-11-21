package com.iteratrlearning.shu_book.kotlin.chapter_02

import java.time.Month

class BankStatementProcessor(private val bankTransactions: List<BankTransaction>) {

    fun calculateTotalAmount(): Double {
        return bankTransactions.sumOf { (it.amount) }
    }

    fun calculateTotalInMonth(month: Month): Double {
        return bankTransactions.filter { month == it.date.month }.sumOf { it.amount }
    }

    fun calculateTotalForCategory(category: String): Double {
        return bankTransactions.filter { category == it.description }.sumOf { it.amount }
    }
}