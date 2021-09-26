package com.iteratrlearning.shu_book.kotlin.chapter_03

interface BankStatementParser {
    fun parseLinesFrom(lines: List<String>): List<BankTransaction>
    fun parseFrom(line: String): BankTransaction
}