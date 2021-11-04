package com.iteratrlearning.shu_book.kotlin.chapter_06

interface UserRepository : AutoCloseable {

    fun add(user: User) : Boolean
    fun get(userId: String) : User?
    fun update(user: User)
    fun clear()
    fun follow(follower: User, userToFollow: User) : FollowStatus
}
