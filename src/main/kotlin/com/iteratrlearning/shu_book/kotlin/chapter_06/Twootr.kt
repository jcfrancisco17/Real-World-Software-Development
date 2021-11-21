package com.iteratrlearning.shu_book.kotlin.chapter_06

import com.iteratrlearning.shu_book.kotlin.chapter_06.Position.Companion.INITIAL_POSITION

class Twootr(val twootRepository: TwootRepository, val userRepository: UserRepository) {

    fun onLogon(userId: String, password: String, receiverEndpoint: ReceiverEndpoint): SenderEndPoint? {
        val authenticatedUser = userRepository.get(userId)?.takeIf { userOfSameId ->
            val hashedPassword = KeyGenerator.hash(password, userOfSameId.salt)
            hashedPassword.contentEquals(userOfSameId.password)
        }

        authenticatedUser?.let { user ->
            user.onLogon(receiverEndpoint)
            twootRepository.query(
                TwootQuery().inUsers(user.following())
                    .lastSeenPosition(user.lastSeenPosition), user::receiveTwoot
            )
            userRepository.update(user)
        }

        return authenticatedUser?.let { user -> SenderEndPoint(user, this) }
    }

    fun onFollow(follow: User, userIdToFollow: String): FollowStatus {
        return userRepository.get(userIdToFollow)?.let { userToFollow -> userRepository.follow(follow, userToFollow) }
            ?: FollowStatus.INVALID_USER
    }

    fun onSendTwoot(id: String, user: User, content: String): Position {
        val userId = user.id
        val twoot = twootRepository.add(id, userId, content)

//        user.followers().

        user.followers()
            .filter { it.isLoggedOn() }
            .forEach { follower ->
                follower.receiveTwoot(twoot)
                userRepository.update(follower)
            }

        return twoot.position
    }

    fun onRegisterUser(userId: String, password: String): RegistrationStatus {
        val salt = KeyGenerator.newSalt()
        val hashedPassword = KeyGenerator.hash(password, salt)
        val user = User(userId, hashedPassword, salt, INITIAL_POSITION)
        return if (userRepository.add(user)) RegistrationStatus.SUCCESS else RegistrationStatus.DUPLICATE
    }

    fun onDeleteTwoot(userId: String, id: String): DeleteStatus {
        return twootRepository.get(id)?.let { twoot ->
            val canDeleteTwoot = twoot.senderId == userId
            if (canDeleteTwoot) {
                twootRepository.delete(twoot)
            }
            if (canDeleteTwoot) DeleteStatus.SUCCESS else DeleteStatus.NOT_YOUR_TWOOT
        } ?: DeleteStatus.UNKNOWN_TWOOT


    }

}
