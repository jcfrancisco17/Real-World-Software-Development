package com.iteratrlearning.shu_book.kotlin.chapter_02

import java.time.Month

class BankStatementProcessor(private val bankTransactions: List<BankTransaction>) {

    fun calculateTotalAmount(): Double {
        return bankTransactions.stream().mapToDouble { it.amount }.sum()
    }

    fun calculateTotalInMonth(month: Month): Double {
        return bankTransactions.stream().filter { month == it.date.month }.mapToDouble { it.amount }.sum()
    }

    fun calculateTotalForCategory(category: String): Double {
        return bankTransactions.stream().filter { category == it.description }.mapToDouble { it.amount }.sum()
    }
}