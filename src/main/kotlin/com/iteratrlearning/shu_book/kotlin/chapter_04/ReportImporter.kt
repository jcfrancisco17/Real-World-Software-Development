package com.iteratrlearning.shu_book.kotlin.chapter_04

import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.BODY
import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.PATIENT
import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.TYPE
import java.io.File

class ReportImporter : Importer {

    companion object {
        private const val NAME_PREFIX = "Patient: "
    }

    override fun importFile(file: File): Document {
        val textFile = TextFile(file)
        textFile.addLineSuffix(NAME_PREFIX, PATIENT)
        textFile.addLines(2, { false }, BODY)

        val attributes = textFile.attributes
        attributes[TYPE] = "REPORT"
        return Document(attributes)
    }
}
