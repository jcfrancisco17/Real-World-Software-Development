package com.iteratrlearning.shu_book.kotlin.chapter_03

import java.util.stream.Collectors.toList


class BankStatementCsvParser : BankStatementParser {

    override fun parseLinesFrom(lines: List<String>): List<BankTransaction> {
        val bankTransactions = lines.stream().map {
            parseFrom(it)
        }.collect(toList())

        return bankTransactions
    }

    override fun parseFrom(line: String): BankTransaction {
        val columns = line.split(",")

        val bankStatementEntry = BankStatementEntry(columns[0], columns[1], columns[2])
        return bankStatementEntry.createBankTransaction()
    }

}