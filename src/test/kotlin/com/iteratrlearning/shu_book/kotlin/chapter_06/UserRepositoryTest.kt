package com.iteratrlearning.shu_book.kotlin.chapter_06

import com.iteratrlearning.shu_book.kotlin.chapter_06.TestData.OTHER_USED_ID
import com.iteratrlearning.shu_book.kotlin.chapter_06.TestData.PASSWORD_BYTES
import com.iteratrlearning.shu_book.kotlin.chapter_06.TestData.SALT
import com.iteratrlearning.shu_book.kotlin.chapter_06.TestData.USER_ID
import com.iteratrlearning.shu_book.kotlin.chapter_06.TestData.twootAt
import com.iteratrlearning.shu_book.kotlin.chapter_06.database.DatabaseUserRepository
import com.iteratrlearning.shu_book.kotlin.chapter_06.in_memory.InMemoryUserRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.funSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk

private lateinit var repositoryUnderTest: UserRepository
private lateinit var receiverEndPoint: ReceiverEndpoint

class UserRepositoryTest : FunSpec({

    include(abstractUserRepositoryTest("in memory user repository") { InMemoryUserRepository() })
    include(abstractUserRepositoryTest("database repository") { DatabaseUserRepository() })

})

fun abstractUserRepositoryTest(name: String, userRepository: () -> UserRepository) = funSpec {

    beforeEach {
        repositoryUnderTest = userRepository.invoke()
        receiverEndPoint = mockk()
        repositoryUnderTest.clear()
    }

    test("$name should load saved users") {
        repositoryUnderTest.add(userWith(USER_ID))

        repositoryUnderTest.get(USER_ID) should { user ->
            user?.id shouldBe USER_ID
            user?.password shouldBe PASSWORD_BYTES
        }
    }

    test("$name should not allow duplicate users") {
        repositoryUnderTest.add(userWith(USER_ID)) shouldBe true
        repositoryUnderTest.add(userWith(USER_ID)) shouldBe false
    }

    test("$name should record follower relationships") {
        val user = userWith(USER_ID)
        val otherUser = userWith(OTHER_USED_ID)

        repositoryUnderTest.add(user)
        repositoryUnderTest.add(otherUser)
        repositoryUnderTest.follow(follower = user, userToFollow = otherUser)

        val userReloaded = repositoryUnderTest.get(USER_ID)!! // throw NPE if null
        val otherUsersReloaded = repositoryUnderTest.get(OTHER_USED_ID)!! // throw NPE if null

        otherUsersReloaded.addFollower(user = userReloaded) shouldBe FollowStatus.ALREADY_FOLLOWING
    }

    test("$name should record position updates") {
        val id = "1"

        every { receiverEndPoint.onTwoot(any()) } just Runs

        val newPosition = Position(2)
        val user = userWith(USER_ID)
        repositoryUnderTest.add(user)
        user.lastSeenPosition shouldBe Position.INITIAL_POSITION

        user.receiveTwoot(twootAt(id, newPosition))
    }

}

private fun userWith(userId: String): User {
    val user = User(userId, PASSWORD_BYTES, SALT, Position.INITIAL_POSITION)
    user.onLogon(receiverEndPoint)
    return user
}
