package com.iteratrlearning.shu_book.kotlin.chapter_03

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import java.time.LocalDate
import java.time.Month

class BankStatementProcessorTest : FunSpec({
    test("should filter transactions in February") {
        val februarySalary = BankTransaction(LocalDate.of(2019, Month.FEBRUARY, 14), 2_000.00, "Salary")
        val marchRoyalties = BankTransaction(LocalDate.of(2019, Month.MARCH, 20), 500.00, "Royalties")

        val bankTransactions = listOf(februarySalary, marchRoyalties)

        val bankStatementProcessor = BankStatementProcessor(bankTransactions)
        val transactions =
            bankStatementProcessor.findTransactions { bankTransaction -> bankTransaction.date.month == Month.FEBRUARY && bankTransaction.amount >= 1_000.00 }

        transactions shouldHaveSize 1
        transactions shouldContainExactly listOf(februarySalary)
    }
})