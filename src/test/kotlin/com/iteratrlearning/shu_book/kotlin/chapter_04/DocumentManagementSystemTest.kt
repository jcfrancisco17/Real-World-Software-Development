package com.iteratrlearning.shu_book.kotlin.chapter_04

import com.iteratrlearning.shu_book.chapter_04.Attributes
import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.ADDRESS
import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.AMOUNT
import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.BODY
import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.HEIGHT
import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.PATIENT
import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.WIDTH
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import java.io.File
import java.io.FileNotFoundException

private const val JOE_BLOGGS = "Joe Bloggs"
private val RESOURCES = "src" + File.separator + "test" + File.separator + "resources" + File.separator
private val LETTER = RESOURCES + "patient.letter"
private val REPORT = RESOURCES + "patient.report"
private val XRAY = RESOURCES + "xray.jpg"
private val INVOICE = RESOURCES + "patient.invoice"

var system = DocumentManagementSystem()

class DocumentManagementSystemTest : FunSpec({

    test("should import file") {
        system.importFile(LETTER)
        onlyDocument()
    }

    test("should import letter attributes") {
        system.importFile(LETTER)

        val document = onlyDocument()

        assertAttributeEquals(document, PATIENT, JOE_BLOGGS)
        assertAttributeEquals(
            document, ADDRESS,
            "123 Fake Street\n" +
                    "Westminster\n" +
                    "London\n" +
                    "United Kingdom"
        )
        assertAttributeEquals(
            document, BODY,
            "We are writing to you to confirm the re-scheduling of your appointment\n" +
                    "with Dr. Avaj from 29th December 2016 to 5th January 2017."
        )
        assertTypeIs("LETTER", document)
    }

    test("should import report attributes") {
        system.importFile(REPORT)

        assertIsReport(onlyDocument())
    }

    test("should import image attributes") {
        system.importFile(XRAY)

        val document = onlyDocument()

        assertAttributeEquals(document, WIDTH, "320")
        assertAttributeEquals(document, HEIGHT, "179")
        assertTypeIs("IMAGE", document)
    }

    test("should import invoice attributes") {
        system.importFile(INVOICE)

        val document = onlyDocument()

        assertAttributeEquals(document, PATIENT, JOE_BLOGGS)
        assertAttributeEquals(document, AMOUNT, "$100")
        assertTypeIs("INVOICE", document)
    }

    test("should be able to search files by attributes") {
        system.importFile(LETTER)
        system.importFile(REPORT)
        system.importFile(XRAY)

        val documents = system.search("patient:Joe,body:Diet Coke")
        documents shouldHaveSize 1
        assertIsReport(documents[0])
    }

    test("should not import missing file") {
        shouldThrow<FileNotFoundException> {
            system.importFile("doesNotExist.txt")
        }
    }

    test("should not import unknown file") {
        shouldThrow<UnknownFileTypeException> {
            system.importFile(RESOURCES + "unknown.txt")
        }
    }


    beforeTest {
        system = DocumentManagementSystem()
    }
})

fun assertIsReport(document: Document) {
    assertAttributeEquals(document, Attributes.PATIENT, JOE_BLOGGS)
    assertAttributeEquals(
        document, Attributes.BODY,
        """
                On 5th January 2017 I examined Joe's teeth.
                We discussed his switch from drinking Coke to Diet Coke.
                No new problems were noted with his teeth.
                """.trimIndent()
    )
    assertTypeIs("REPORT", document)
}

fun assertAttributeEquals(document: Document, attributeName: String, expectedValue: String) {
    document.getAttribute(attributeName) shouldBe expectedValue
}

// end::assertAttributeEquals[]
fun assertTypeIs(type: String, document: Document) {
    assertAttributeEquals(document, Attributes.TYPE, type)
}

fun onlyDocument(): Document {
    val documents = system.contents()
    documents shouldHaveSize 1
    return documents[0]
}
