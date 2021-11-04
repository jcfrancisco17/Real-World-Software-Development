package com.iteratrlearning.shu_book.kotlin.chapter_06.database

import java.sql.PreparedStatement

fun interface Extractor<R> {
    fun run(statement: PreparedStatement): R
}