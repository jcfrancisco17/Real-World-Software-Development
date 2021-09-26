package com.iteratrlearning.shu_book.kotlin.chapter_03

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class NotificationTest : FunSpec({
    test("can add errors without exception") {
        val notification = Notification()
        notification.addError("error 1")
        notification.addError("error 2")
    }

    test("hasErrors returns true if there are errors") {
        val notification = Notification()
        notification.addError("error 1")

        notification.hasErrors() shouldBe true
    }

    test("hasErrors returns false if there are no errors") {
        val notification = Notification()

        notification.hasErrors() shouldBe false
    }

    test("errorMessage prints the errors") {
        val notification = Notification()
        notification.addError("error 1")
        notification.addError("error 2")

        notification.errorMessage() shouldBe "[error 1, error 2]"
    }

    test("getErrors returns a copy of the errors") {
        val notification = Notification()
        notification.addError("error 1")

        notification.getErrors() shouldBe listOf("error 1")

//        notification.getErrors.add()
//        Since getErrors returns an ArrayList, which is immutable in Kotlin, it does not have the add method
    }
})