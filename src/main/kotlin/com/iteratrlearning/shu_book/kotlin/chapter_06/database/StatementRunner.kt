package com.iteratrlearning.shu_book.kotlin.chapter_06.database

import java.sql.*

class StatementRunner(val conn: Connection) {

    fun update(sql: String) {
        withStatement(sql) { stmt: PreparedStatement -> stmt.execute() }
    }

    fun query(sql: String, withPreparedStatement: With<ResultSet>) {
        withStatement(sql) { stmt ->
            val resultSet = stmt.executeQuery()
            while (resultSet.next()) {
                withPreparedStatement.run(resultSet)
            }
        }
    }

    fun withStatement(sql: String, withPreparedStatement: With<PreparedStatement>) {
        extract(sql) { stmt: PreparedStatement ->
            withPreparedStatement.run(stmt)
        }
    }

    fun <R> extract(sql: String, extractor: Extractor<R>): R {
        try {
            conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                .use { //had to add kotlin-stdlib-jdk8 for use extension function to be available. https://www.baeldung.com/kotlin/try-with-resources#how-to
                    it.clearParameters()
                    return extractor.run(it)
                }
        } catch (e: SQLException) {
            throw IllegalStateException(e)
        }
    }
}