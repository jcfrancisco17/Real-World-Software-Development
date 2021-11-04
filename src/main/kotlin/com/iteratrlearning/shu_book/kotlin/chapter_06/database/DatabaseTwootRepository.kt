package com.iteratrlearning.shu_book.kotlin.chapter_06.database

import com.iteratrlearning.shu_book.kotlin.chapter_06.Position
import com.iteratrlearning.shu_book.kotlin.chapter_06.Twoot
import com.iteratrlearning.shu_book.kotlin.chapter_06.TwootQuery
import com.iteratrlearning.shu_book.kotlin.chapter_06.TwootRepository
import java.sql.ResultSet
import java.sql.SQLException
import java.util.function.Consumer

class DatabaseTwootRepository : TwootRepository {

    private var position = Position.INITIAL_POSITION

    private var statementRunner: StatementRunner = try {
        val conn = DatabaseConnection.get()
        conn.createStatement()
            .executeUpdate(
                """
                    CREATE TABLE IF NOT EXISTS 
                    twoots (
                        position INT IDENTITY,
                        id VARCHAR(36) UNIQUE NOT NULL,
                        senderId VARCHAR(15) NOT NULL,
                        content VARCHAR(140) NOT NULL
                    )
                """.trimIndent()
            )
        StatementRunner(conn)
    } catch (e: SQLException) {
        throw RuntimeException(e)
    }

    override fun add(id: String, userId: String, content: String): Twoot {
        statementRunner.withStatement("INSERT INTO twoots (id, senderId, content) VALUES (?,?,?)") { stmt ->
            stmt.setString(1, id)
            stmt.setString(2, userId)
            stmt.setString(3, content)
            stmt.executeUpdate()
            val rs = stmt.generatedKeys
            if (rs.next()) {
                position = Position(rs.getInt(1))
            }
        }

        return Twoot(id, userId, content, position)
    }

    override fun get(id: String): Twoot? {
        return statementRunner.extract("SELECT * FROM twoots WHERE id = ?") { stmt ->
            stmt.setString(1, id)
            val resultSet = stmt.executeQuery()
            return@extract if (resultSet.next()) extractTwoot(resultSet) else null
        }
    }

    override fun delete(twoot: Twoot) {
        statementRunner.withStatement("DELETE FROM twoots WHERE position = ?") { stmt ->
            stmt.setInt(1, position.value)
            stmt.executeUpdate()
        }
    }

    override fun query(twootQuery: TwootQuery, callback: Consumer<Twoot>) {
        if (!twootQuery.hasUsers()) {
            return
        }

        val lastSeenPosition = twootQuery.lastSeenPosition
        val inUsers = twootQuery.inUsers

        val sql = """SELECT * FROM twoots WHERE senderId IN ${usersTuples(inUsers)} 
                AND twoots.position > ${lastSeenPosition.value}
                """.trimIndent()
        statementRunner.query(sql) { rs -> callback.accept(extractTwoot(rs)) }

    }

    private fun usersTuples(inUsers: Set<String>) =
        inUsers.joinToString(separator = ",", prefix = "(", postfix = ")") { "'$it'" }

    private fun extractTwoot(rs: ResultSet): Twoot {
        val position = Position(rs.getInt(1))
        val id = rs.getString(2)
        val senderId = rs.getString(3)
        val content = rs.getString(4)
        return Twoot(id, senderId, content, position)
    }

    override fun clear() {
        statementRunner.update("DELETE FROM twoots")
        statementRunner.update("DROP SCHEMA PUBLIC CASCADE")
    }
}