package com.iteratrlearning.shu_book.kotlin.chapter_04

import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.AMOUNT
import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.PATIENT
import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.TYPE
import java.io.File

class InvoiceImporter : Importer {
    val NAME_PREFIX = "Dear "
    val AMOUNT_PREFIX = "Amount: "

    override fun importFile(file: File): Document {
        val textFile = TextFile(file)

        textFile.addLineSuffix(NAME_PREFIX, PATIENT)
        textFile.addLineSuffix(AMOUNT_PREFIX, AMOUNT)

        val attributes = textFile.attributes
        attributes[TYPE] = "INVOICE"
        return Document(attributes)
    }

}
