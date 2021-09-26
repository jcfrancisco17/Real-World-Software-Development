package com.iteratrlearning.shu_book.kotlin.chapter_03

fun main() {

    val bankStatementAnalyzer = BankStatementAnalyzer()
    val bankStatementParser = BankStatementCsvParser()

    bankStatementAnalyzer.analyze("bank-data-simple.csv", bankStatementParser)
}