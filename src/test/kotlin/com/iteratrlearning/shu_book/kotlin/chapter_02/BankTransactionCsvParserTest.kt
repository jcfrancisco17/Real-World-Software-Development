package com.iteratrlearning.shu_book.kotlin.chapter_02

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.Month


class BankTransactionCsvParserTest : FunSpec({

    test("should parse one correct line") {
        val line = "30-01-2017,-50,Tesco"

        val bankStatementParser: BankStatementParser = BankStatementCsvParser()

        val result = bankStatementParser.parseFrom(line)

        val expected = BankTransaction(
            LocalDate.of(2017, Month.JANUARY, 30),
            -50.00,
            "Tesco"
        )
        result.date shouldBe expected.date
        result.amount shouldBe expected.amount
        result.description shouldBe expected.description
    }

})

