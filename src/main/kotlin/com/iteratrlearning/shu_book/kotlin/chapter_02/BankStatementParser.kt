package com.iteratrlearning.shu_book.kotlin.chapter_02

interface BankStatementParser {
    fun parseFrom(line: String): BankTransaction
    fun parseLinesFrom(lines: List<String>): List<BankTransaction>
}