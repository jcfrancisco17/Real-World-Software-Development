package com.iteratrlearning.shu_book.kotlin.chapter_06

import com.iteratrlearning.shu_book.kotlin.chapter_06.TestData.NOT_A_USER
import com.iteratrlearning.shu_book.kotlin.chapter_06.TestData.OTHER_USED_ID
import com.iteratrlearning.shu_book.kotlin.chapter_06.TestData.PASSWORD
import com.iteratrlearning.shu_book.kotlin.chapter_06.TestData.TWOOT
import com.iteratrlearning.shu_book.kotlin.chapter_06.TestData.USER_ID
import com.iteratrlearning.shu_book.kotlin.chapter_06.TestData.twootAt
import com.iteratrlearning.shu_book.kotlin.chapter_06.in_memory.InMemoryTwootRepository
import com.iteratrlearning.shu_book.kotlin.chapter_06.in_memory.InMemoryUserRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.*

private lateinit var twootr: Twootr
private lateinit var receiverEndPoint: ReceiverEndpoint
private lateinit var twootRepository: InMemoryTwootRepository
private lateinit var userRepository: InMemoryUserRepository
private lateinit var endPoint: SenderEndPoint

private val POSITION_1 = Position(0)

class TwootrTest : FunSpec({

    beforeEach {
        receiverEndPoint = mockk()
        twootRepository = spyk()
        userRepository = InMemoryUserRepository()
        twootr = Twootr(twootRepository, userRepository)

        twootr.onRegisterUser(USER_ID, PASSWORD) shouldBe RegistrationStatus.SUCCESS
        twootr.onRegisterUser(OTHER_USED_ID, PASSWORD) shouldBe RegistrationStatus.SUCCESS
    }

    test("should not register duplicate user") {
        twootr.onRegisterUser(USER_ID, PASSWORD) shouldBe RegistrationStatus.DUPLICATE
    }

    test("should be able to authenticate user") {
        logon()
    }

    test("should not authenticate user with wrong password") {
        val senderEndPoint = twootr.onLogon(USER_ID, "wrong password", receiverEndPoint)
        senderEndPoint shouldBe null
    }

    test("should not authenticate unknown user") {
        val senderEndPoint = twootr.onLogon(NOT_A_USER, PASSWORD, receiverEndPoint)
        senderEndPoint shouldBe null
    }

    test("should follow a valid user") {
        logon()

        endPoint.onFollow(OTHER_USED_ID) shouldBe FollowStatus.SUCCESS
    }

    test("should not duplicate follow valid user") {
        logon()

        endPoint.onFollow(OTHER_USED_ID)

        endPoint.onFollow(OTHER_USED_ID) shouldBe FollowStatus.ALREADY_FOLLOWING
    }

    test("should not follow invalid used") {
        logon()

        endPoint.onFollow(NOT_A_USER) shouldBe FollowStatus.INVALID_USER
    }

    test("should receive twoots from followed user") {
        val id = "1"

        logon()

        endPoint.onFollow(OTHER_USED_ID)

        every { receiverEndPoint.onTwoot(any()) } just Runs

        val otherEndPoint = otherLogon()
        otherEndPoint.onSendTwoot(id, TWOOT)

        verify { twootRepository.add(id, OTHER_USED_ID, TWOOT) }
        verify { receiverEndPoint.onTwoot(Twoot(id, OTHER_USED_ID, TWOOT, Position(0))) }
    }

    test("should not receive twoots after logoff") {
        val id = "1"

        userFollowsOtherUser()

        val otherEndPoint = otherLogon()
        otherEndPoint.onSendTwoot(id, TWOOT)

        verify(exactly = 0) { receiverEndPoint.onTwoot(any()) }
    }

    test("should receive replay of twoots after logoff") {
        val id = "1"

        userFollowsOtherUser()

        val otherEndPoint = otherLogon()
        otherEndPoint.onSendTwoot(id, TWOOT)

        every { receiverEndPoint.onTwoot(any()) } just Runs

        logon()

        verify { receiverEndPoint.onTwoot(twootAt(id, POSITION_1)) }
    }

    test("should delete twoots") {
        val id = "1"

        userFollowsOtherUser()

        val otherEndPoint = otherLogon()
        otherEndPoint.onSendTwoot(id, TWOOT)
        val status = otherEndPoint.onDeleteTwoot(id)

        logon()

        status shouldBe DeleteStatus.SUCCESS
        verify(exactly = 0) { receiverEndPoint.onTwoot(any()) }
    }

    test("should not delete future position twoots") {
        logon()

        endPoint.onDeleteTwoot("DAS") shouldBe DeleteStatus.UNKNOWN_TWOOT
    }

    test("should not delete other users twoots") {
        val id = "1"

        logon()

        val otherEndpoint = otherLogon()
        otherEndpoint.onSendTwoot(id, TWOOT)

        endPoint.onDeleteTwoot(id) shouldBe DeleteStatus.NOT_YOUR_TWOOT
        twootRepository.get(id) shouldNotBe null
    }

})

fun otherLogon(): SenderEndPoint = logon(OTHER_USED_ID, mockk())


fun userFollowsOtherUser() {
    logon()

    endPoint.onFollow(OTHER_USED_ID)

    endPoint.onLogOff()
}

fun logon() {
    endPoint = logon(USER_ID, receiverEndPoint)
}

fun logon(userId: String, receiverEndpoint: ReceiverEndpoint): SenderEndPoint {
    val endPoint = twootr.onLogon(userId, PASSWORD, receiverEndpoint)
    endPoint shouldNotBe null
    return endPoint!!

}