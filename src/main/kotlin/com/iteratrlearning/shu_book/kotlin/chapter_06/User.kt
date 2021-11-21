package com.iteratrlearning.shu_book.kotlin.chapter_06

class User(val id: String, val password: ByteArray, val salt: ByteArray, var lastSeenPosition: Position) {
    private var receiverEndpoint: ReceiverEndpoint? = null
    private val following = mutableSetOf<String>()
    private val followers = mutableSetOf<User>()

//    fun followers(): Stream<User> {
//        return followers.stream()
//    }

    fun followers(): Sequence<User> {
        return followers.asSequence()
    }

    fun following(): Set<String> {
        return following
    }

    fun isLoggedOn(): Boolean {
        return receiverEndpoint != null
    }

    fun receiveTwoot(twoot: Twoot): Boolean {
        if (isLoggedOn()) {
            receiverEndpoint!!.onTwoot(twoot)
            lastSeenPosition = twoot.position
            return true
        }
        return false
    }

    fun onLogon(receiverEndpoint: ReceiverEndpoint) {
        this.receiverEndpoint = receiverEndpoint
    }

    fun addFollower(user: User): FollowStatus {
        return if (followers.add(user)) {
            user.following.add(id)
            FollowStatus.SUCCESS // Implied return
        } else {
            FollowStatus.ALREADY_FOLLOWING // Implied return
        }
    }

    fun logOff() {
        receiverEndpoint = null
    }

}
