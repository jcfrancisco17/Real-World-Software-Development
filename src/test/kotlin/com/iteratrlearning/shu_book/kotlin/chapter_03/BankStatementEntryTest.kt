package com.iteratrlearning.shu_book.kotlin.chapter_03

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEqualIgnoringCase

class BankStatementEntryTest : FunSpec({

    test("notification should have no errors if all fields are valid") {
        val validator = BankStatementEntry(description = "description", date = "25-09-2021", amount = "1000.00")
        val notification = validator.validate()

        notification.getErrors().isEmpty() shouldBe true
    }

    test("notification should have description error if description is greater than 100 characters") {
        val descriptionLongerThan100Characters =
            "description description description description description description description description description"
        val validator = BankStatementEntry(
            description = descriptionLongerThan100Characters,
            date = "25-09-2021",
            amount = "1000.00"
        )
        val notification = validator.validate()

        notification.getErrors().size shouldBe 1
        notification.getErrors()[0] shouldBeEqualIgnoringCase "The description is too long"
    }

    test("notification should have date error if date cannot be parsed") {
        val validator =
            BankStatementEntry(description = "description", date = "invalid-date-format", amount = "1000.00")
        val notification = validator.validate()

        notification.getErrors().size shouldBe 1
        notification.getErrors()[0] shouldBeEqualIgnoringCase "Invalid format for date"
    }

    test("notification should have date error if date is in the future:") {
        val futureDate = "31-12-9999"
        val validator = BankStatementEntry(description = "description", date = futureDate, amount = "1000.00")
        val notification = validator.validate()

        notification.getErrors().size shouldBe 1
        notification.getErrors()[0] shouldBeEqualIgnoringCase "Date cannot be in the future"
    }

    test("notification should have amount error if amount is not of type Double:") {
        val invalidAmount = "ABC"
        val validator = BankStatementEntry(description = "description", date = "25-09-2021", amount = invalidAmount)
        val notification = validator.validate()

        notification.getErrors().size shouldBe 1
        notification.getErrors()[0] shouldBeEqualIgnoringCase "Invalid format for amount"
    }

    test("notification has all invalid fields should have 3 errors") {
        val futureDate = "31-12-9999"
        val invalidAmount = "ABC"
        val descriptionLongerThan100Characters =
            "description description description description description description description description description"
        val validator = BankStatementEntry(
            description = descriptionLongerThan100Characters,
            date = futureDate,
            amount = invalidAmount
        )
        val notification = validator.validate()

        notification.getErrors().size shouldBe 3
        notification.getErrors() shouldContainExactlyInAnyOrder listOf(
            "The description is too long",
            "Date cannot be in the future",
            "Invalid format for amount"
        )
    }

})