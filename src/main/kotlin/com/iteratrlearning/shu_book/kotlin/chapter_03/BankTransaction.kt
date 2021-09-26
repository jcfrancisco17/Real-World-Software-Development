package com.iteratrlearning.shu_book.kotlin.chapter_03

import java.time.LocalDate
import java.util.*

class BankTransaction(val date: LocalDate, val amount: Double, val description: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BankTransaction

        if (date != other.date) return false
        if (amount != other.amount) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(date, amount, description)
    }

    override fun toString(): String {
        return "BankTransactionK(date=$date, amount=$amount, description='$description')"
    }


}