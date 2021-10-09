package com.iteratrlearning.shu_book.kotlin.chapter_04

import java.io.File
import java.nio.file.Files
import java.util.function.Predicate

class TextFile(file: File) {
    val attributes: MutableMap<String, String> = mutableMapOf()
    private val lines: MutableList<String>

    init {
        attributes[Attributes.PATH] = file.path
        lines = Files.lines(file.toPath()).toList()
    }

    fun addLines(start: Int, isEnd: Predicate<String>, attributeName: String): Int {
        val accumulator = StringBuilder()
        var end = start
        for (lineNumber in start until lines.size) {
            if (isEnd.test(lines[lineNumber])) {
                end = lineNumber
                break
            }
            accumulator.append(lines[lineNumber])
            accumulator.append("\n")
        }
        attributes[attributeName] = accumulator.toString().trim()
        return end
    }

    fun addLineSuffix(prefix: String, attributeName: String) {
        for (line in lines) {
            if (line.startsWith(prefix)) {
                attributes[attributeName] = line.substring(prefix.length)
                break
            }
        }
    }

}
