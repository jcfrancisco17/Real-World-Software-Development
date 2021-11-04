package com.iteratrlearning.shu_book.kotlin.chapter_06.database

import java.sql.Connection
import java.sql.DriverManager

object DatabaseConnection {

    init {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver")
        } catch (e: ClassNotFoundException) {
            throw Error(e)
        }
    }

    fun get(): Connection {
        return DriverManager.getConnection("jdbc:hsqldb:mem:mydatabase", "SA", "")
    }
}