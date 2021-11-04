package com.iteratrlearning.shu_book.kotlin.chapter_06.database

import com.iteratrlearning.shu_book.kotlin.chapter_06.FollowStatus
import com.iteratrlearning.shu_book.kotlin.chapter_06.Position
import com.iteratrlearning.shu_book.kotlin.chapter_06.User
import com.iteratrlearning.shu_book.kotlin.chapter_06.UserRepository
import java.sql.Connection
import java.sql.SQLException

class DatabaseUserRepository : UserRepository {

    private val ID = 1
    private val PASSWORD = 2
    private val SALT = 3
    private val POSITION = 4

    private val FOLLOWER = 1
    private val USER_TO_FOLLOW = 2

    //no need for lateinit because these got initialized in the init block
    private var statementRunner: StatementRunner
    private var conn: Connection
    private var userIdToUser: MutableMap<String, User>

    init {
        try {
            conn = DatabaseConnection.get()
            createTables()
            statementRunner = StatementRunner(conn)
        } catch (e: SQLException) {
            throw IllegalStateException(e)
        }
        userIdToUser = loadFromDatabase()
    }

    private fun createTables() {
        conn.createStatement().executeUpdate(
            """
                CREATE TABLE IF NOT EXISTS users (
                  id VARCHAR(15) NOT NULL,
                  password VARBINARY(20) NOT NULL,
                  salt VARBINARY(16) NOT NULL,
                  position INT NOT NULL
                )
            """.trimIndent()
        )
        conn.createStatement().executeUpdate(
            """
                CREATE TABLE IF NOT EXISTS followers (
                  follower VARCHAR(15) NOT NULL,
                  userToFollow VARCHAR(15) NOT NULL
                )
            """.trimIndent()
        )
    }

    private fun loadFromDatabase(): MutableMap<String, User> {
        val users = mutableMapOf<String, User>()
        statementRunner.query("SELECT id, password, salt, position FROM users") { resultSet ->
            val id = resultSet.getString(ID)
            val password = resultSet.getBytes(PASSWORD)
            val salt = resultSet.getBytes(SALT)
            val position = Position(resultSet.getInt(POSITION))
            val user = User(id, password, salt, position)
            users[id] = user
        }
        statementRunner.query("SELECT follower, userToFollow FROM followers") { resultSet ->
            val followerId = resultSet.getString(FOLLOWER)
            val userToFollowId = resultSet.getString(USER_TO_FOLLOW)
            val follower = users[followerId]!!
            val userToFollow = users[userToFollowId]!!
            userToFollow.addFollower(follower)
        }
        return users
    }

    override fun add(user: User): Boolean {
        val userId = user.id
        val success = userIdToUser.putIfAbsent(userId, user) == null

        if (success) {
            statementRunner.withStatement(
                "INSERT INTO users (id, password, salt, position) VALUES(?,?,?,?)"
            ) { stmt ->
                stmt.setString(ID, userId)
                stmt.setBytes(PASSWORD, user.password)
                stmt.setBytes(SALT, user.salt)
                stmt.setInt(POSITION, user.lastSeenPosition.value)
                stmt.executeUpdate()
            }
        }

        return success
    }

    override fun get(userId: String): User? {
        return userIdToUser[userId]
    }

    override fun update(user: User) {
        statementRunner.withStatement(
            "UPDATE users SET position=? WHERE id=?"
        ) { stmt ->
            stmt.setInt(1, user.lastSeenPosition.value)
            stmt.setString(2, user.id)
            stmt.executeUpdate()
        }
    }

    override fun clear() {
        statementRunner.update("delete from users")
        statementRunner.update("delete from followers")
        userIdToUser.clear()
    }

    override fun follow(follower: User, userToFollow: User): FollowStatus {
        val followStatus = userToFollow.addFollower(follower)
        if (followStatus == FollowStatus.SUCCESS) {
            statementRunner.withStatement(
                "INSERT INTO followers (follower, userToFollow) VALUES (?,?)"
            ) { stmt ->
                stmt.setString(FOLLOWER, follower.id)
                stmt.setString(USER_TO_FOLLOW, userToFollow.id)
                stmt.executeUpdate()
            }
        }
        return followStatus
    }

    override fun close() {
        conn.close()
    }

}
