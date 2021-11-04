package com.iteratrlearning.shu_book.kotlin.chapter_06.database

fun interface With<P> {
    fun run(stmt: P)
}
