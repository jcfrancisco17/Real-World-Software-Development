package com.iteratrlearning.shu_book.kotlin.chapter_03


class BankStatementCsvParser : BankStatementParser {

    override fun parseLinesFrom(lines: List<String>): List<BankTransaction> {
        return lines.map { parseFrom(it) }.toList()
    }

    override fun parseFrom(line: String): BankTransaction {
        val columns = line.split(",")

        val bankStatementEntry = BankStatementEntry(columns[0], columns[1], columns[2])
        return bankStatementEntry.createBankTransaction()
    }

}