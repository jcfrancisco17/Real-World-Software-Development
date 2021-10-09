package com.iteratrlearning.shu_book.kotlin.chapter_04

import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.ADDRESS
import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.BODY
import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.PATIENT
import com.iteratrlearning.shu_book.kotlin.chapter_04.Attributes.TYPE
import java.io.File

class LetterImporter() : Importer {

    private val NAME_PREFIX = "Dear "

    override fun importFile(file: File): Document {
        val textFile = TextFile(file)

        textFile.addLineSuffix(NAME_PREFIX, PATIENT)

        val lineNumber = textFile.addLines(2, String::isEmpty, ADDRESS)
        textFile.addLines(lineNumber + 1, { line -> line.startsWith("regards,") }, BODY)

        val attributes = textFile.attributes
        attributes[TYPE] = "LETTER"
        return Document(attributes)
    }
}
