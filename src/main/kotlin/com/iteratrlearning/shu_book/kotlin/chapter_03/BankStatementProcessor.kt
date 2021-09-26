package com.iteratrlearning.shu_book.kotlin.chapter_03

import java.time.Month
import java.util.stream.Collectors.toList

class BankStatementProcessor(private val bankTransactions: List<BankTransaction>) {

    fun summarizeTransactions(bankTransactionSummarizer: BankTransactionSummarizer): Double {
        var result = 0.00
        for (bankTransaction in bankTransactions) {
            result = bankTransactionSummarizer.summarize(result, bankTransaction)
        }

        return result
    }

    fun calculateTotalAmount(): Double {
        return bankTransactions.stream().mapToDouble { it.amount }.sum()
    }

    fun calculateTotalInMonth(month: Month): Double {
        return bankTransactions.stream().filter { month == it.date.month }.mapToDouble { it.amount }.sum()
    }

    fun calculateTotalForCategory(category: String): Double {
        return bankTransactions.stream().filter { category == it.description }.mapToDouble { it.amount }.sum()
    }

    fun findTransactionsGreaterThanEquals(amount: Int): List<BankTransaction> {
        return findTransactions { it.amount >= amount }
    }

    fun findTransactionsInMonth(month: Month): List<BankTransaction> {
        return bankTransactions.stream().filter { it.date.month == month }.collect(toList())
    }

    fun findTransactions(bankTransactionFilter: BankTransactionFilter): List<BankTransaction> {
        return bankTransactions.stream().filter { bankTransactionFilter.test(it) }.collect(toList())
    }


}