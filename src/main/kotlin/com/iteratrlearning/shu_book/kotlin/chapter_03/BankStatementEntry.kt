package com.iteratrlearning.shu_book.kotlin.chapter_03

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.properties.Delegates

private val DATE_PATTERN: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

/**
 * This class is actually the `BankStatementValidator` in the book. However, I renamed it to `BankStatementEntry` since
 * each instance of this class corresponds to a single entry in the record.
 *
 * As of this writing, there's no sample code on how the `BankStatementValidator` integrates with the application so this is my
 * take on it.
 */
class BankStatementEntry(val date: String, val amount: String, val description: String) {

    private val notification: Notification = Notification()
    private lateinit var validatedDate: LocalDate
    private var validatedAmount: Double by Delegates.notNull()
    private lateinit var validatedDescription: String

    /**
     * Validates the arguments and throws [InvalidBankStatementEntryException] if invalid arguments were found
     *
     * @throws InvalidBankStatementEntryException
     */
    fun check() {
        val notification = validate()
        if (notification.hasErrors()) throw InvalidBankStatementEntryException(notification.errorMessage())
    }

    /**
     * Validates the arguments and returns a [Notification] which contains found errors.
     *
     * @return Notification
     */
    fun validate(): Notification {
        validateDate()
        validateAmount()
        validateDescription()

        return notification
    }

    /**
     * Validates the arguments and throws [InvalidBankStatementEntryException] if invalid arguments were found.
     *
     * Creates [BankTransaction] from the arguments if none were found.
     *
     * @throws InvalidBankStatementEntryException
     */
    fun createBankTransaction(): BankTransaction {
        check()
        return BankTransaction(validatedDate, validatedAmount, validatedDescription)
    }

    private fun validateDate() {
        try {
            validatedDate = LocalDate.parse(date, DATE_PATTERN)
            if (validatedDate.isAfter(LocalDate.now())) {
                notification.addError("Date cannot be in the future")
            }
        } catch (e: DateTimeParseException) {
            notification.addError("Invalid format for date")
        }
    }

    private fun validateAmount() {
        try {
            validatedAmount = amount.toDouble()
        } catch (e: NumberFormatException) {
            notification.addError("Invalid format for amount")
        }
    }

    private fun validateDescription() {
        if (description.length > 100) {
            notification.addError("The description is too long")
        } else {
            validatedDescription = description
        }
    }

}