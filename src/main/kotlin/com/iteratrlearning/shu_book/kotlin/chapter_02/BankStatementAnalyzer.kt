package com.iteratrlearning.shu_book.kotlin.chapter_02

import java.nio.file.Files
import java.nio.file.Paths
import java.time.Month

class BankStatementAnalyzer {

    companion object {
        private const val RESOURCES = "src/main/resources/"
    }

    fun analyze(fileName: String, bankStatementParser: BankStatementParser) {

        val path = Paths.get(RESOURCES + fileName)
        val lines = Files.readAllLines(path)

        val bankTransactions = bankStatementParser.parseLinesFrom(lines)
        val bankStatementProcessor = BankStatementProcessor(bankTransactions)

        collectSummary(bankStatementProcessor)
    }

    private fun collectSummary(bankStatementProcessor: BankStatementProcessor) {
        println("The total for all transactions is ${bankStatementProcessor.calculateTotalAmount()}")
        println("The total for transactions in January is ${bankStatementProcessor.calculateTotalInMonth(Month.JANUARY)}")
        println("The total for transactions in February is ${bankStatementProcessor.calculateTotalInMonth(Month.FEBRUARY)}")
        println("The total salary received is ${bankStatementProcessor.calculateTotalForCategory("Salary")}")
    }
}

