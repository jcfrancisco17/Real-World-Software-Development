package com.iteratrlearning.shu_book.kotlin.chapter_02

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors.toList

class BankStatementCsvParser : BankStatementParser {

    @JvmField
    val DATE_PATTERN: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    override fun parseFrom(line: String): BankTransaction {
        val columns = line.split(",")

        val date = LocalDate.parse(columns[0], DATE_PATTERN)
        val amount = columns[1].toDouble()
        val description = columns[2]

        return BankTransaction(date, amount, description)
    }

    override fun parseLinesFrom(lines: List<String>): List<BankTransaction> {
        val bankTransactions = lines.stream().map {
            parseFrom(it)
        }.collect(toList())

        return bankTransactions
    }

}