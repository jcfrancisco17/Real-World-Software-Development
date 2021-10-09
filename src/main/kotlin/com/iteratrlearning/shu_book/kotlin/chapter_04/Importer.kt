package com.iteratrlearning.shu_book.kotlin.chapter_04

import java.io.File

/**
 * The original Importer Java interface throws IOException. However,
 * [Kotlin does not have checked exceptions](https://kotlinlang.org/docs/java-to-kotlin-interop.html#checked-exceptions)
 * so there's no "throws" in this function.
 *
 * For Java interop, we can use `@Throws(SomeException::class)` to make the Java compiler happy.
 */
interface Importer {
    fun importFile(file : File) : Document
}
