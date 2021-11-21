package com.iteratrlearning.shu_book.kotlin.chapter_03

import java.time.Month

class BankStatementProcessor(private val bankTransactions: List<BankTransaction>) {

    fun summarizeTransactions(bankTransactionSummarizer: BankTransactionSummarizer): Double {
        var result = 0.00
        for (bankTransaction in bankTransactions) {
            result = bankTransactionSummarizer.summarize(result, bankTransaction)
        }

        return result
    }

    fun calculateTotalAmount(): Double {
        return bankTransactions.sumOf { it.amount }
    }

    fun calculateTotalInMonth(month: Month): Double {
        return bankTransactions.filter { month == it.date.month }.sumOf { it.amount }
    }

    fun calculateTotalForCategory(category: String): Double {
        return bankTransactions.filter { category == it.description }.sumOf { it.amount }
    }

    @Suppress("unused")
    fun findTransactionsGreaterThanEquals(amount: Int): List<BankTransaction> {
        return findTransactions { it.amount >= amount }
    }

    @Suppress("unused")
    fun findTransactionsInMonth(month: Month): List<BankTransaction> {
        return bankTransactions.filter { it.date.month == month }.toList()
    }

    fun findTransactions(bankTransactionFilter: BankTransactionFilter): List<BankTransaction> {
        return bankTransactions.filter { bankTransactionFilter.test(it) }.toList()
    }


}