package com.iteratrlearning.shu_book.kotlin.chapter_04

import java.io.File
import java.io.FileNotFoundException

class DocumentManagementSystem {

    private val documents = mutableListOf<Document>()

    private val extensionImporter: Map<String, Importer> = mapOf(
        "letter" to LetterImporter(),
        "report" to ReportImporter(),
        "jpg" to ImageImporter(),
        "invoice" to InvoiceImporter()
    )

    fun importFile(path: String) {
        val file = File(path)
        if (!file.exists()) throw FileNotFoundException(path)

        val separatorIndex = path.lastIndexOf('.')
        if (separatorIndex != -1) {
            if (separatorIndex == path.length) throw UnknownFileTypeException("No extension found for file: $path")
            val extension = path.substring(separatorIndex + 1)
            val importer = extensionImporter[extension] ?: throw UnknownFileTypeException("For file: $path")
            val document = importer.importFile(file)
            documents.add(document)
        } else {
            throw UnknownFileTypeException("No extension found for file: $path")
        }
    }

    fun contents(): List<Document> {
        return documents.toList()
    }

    fun search(query: String): List<Document> {
        return documents.stream().filter(Query.parse(query)).toList()
    }
}
