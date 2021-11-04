package com.iteratrlearning.shu_book.kotlin.chapter_06.in_memory

import com.iteratrlearning.shu_book.kotlin.chapter_06.FollowStatus
import com.iteratrlearning.shu_book.kotlin.chapter_06.User
import com.iteratrlearning.shu_book.kotlin.chapter_06.UserRepository

class InMemoryUserRepository : UserRepository {

    private val userIdToUser = mutableMapOf<String, User>()

    override fun add(user: User): Boolean {
        return userIdToUser.putIfAbsent(user.id, user) == null
    }

    override fun get(userId: String): User? {
        return userIdToUser[userId]
    }

    override fun update(user: User) {
        // Deliberately blank in original code
    }

    override fun clear() {
        userIdToUser.clear()
    }

    override fun follow(follower: User, userToFollow: User): FollowStatus {
        return userToFollow.addFollower(follower)
    }

    override fun close() {
        TODO("Not yet implemented")
    }

}
