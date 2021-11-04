package com.iteratrlearning.shu_book.kotlin.chapter_06

class SenderEndPoint(private val user: User, private val twootr: Twootr) {


    fun onFollow(userIdToFollow: String): FollowStatus {
        return twootr.onFollow(user, userIdToFollow)
    }

    fun onSendTwoot(id: String, content: String): Position {
        return twootr.onSendTwoot(id, user, content)
    }

    fun onLogOff() {
        user.logOff()
    }

    fun onDeleteTwoot(id: String): DeleteStatus {
        return twootr.onDeleteTwoot(user.id, id)

    }

}
