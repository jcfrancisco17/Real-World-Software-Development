package com.iteratrlearning.shu_book.kotlin.chapter_06

import com.iteratrlearning.shu_book.kotlin.chapter_06.TestData.TWOOT
import com.iteratrlearning.shu_book.kotlin.chapter_06.TestData.TWOOT_2
import com.iteratrlearning.shu_book.kotlin.chapter_06.TestData.USER_ID
import com.iteratrlearning.shu_book.kotlin.chapter_06.database.DatabaseTwootRepository
import com.iteratrlearning.shu_book.kotlin.chapter_06.in_memory.InMemoryTwootRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.funSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.*
import java.util.function.Consumer


private lateinit var repository: TwootRepository
private lateinit var callback: Consumer<Twoot>
private val twootQuery = TwootQuery()

class TwootRepositoryTest : FunSpec({

    include(abstractTwootRepositoryTest("in memory twoot repository") { InMemoryTwootRepository() })
    include(abstractTwootRepositoryTest("database twoot repository") { DatabaseTwootRepository() })
})

fun abstractTwootRepositoryTest(name: String, twootRepository: () -> TwootRepository) = funSpec {

    beforeEach {
        callback = mockk()
        repository = twootRepository.invoke()
    }

    test("$name should load twoots from position") {
        val position = add("1", TWOOT)
        val position2 = add("2", TWOOT_2)

        every { callback.accept(any()) } just Runs

        repository.query(twootQuery.inUsers(USER_ID).lastSeenPosition(position), callback)

        verifyAll { callback.accept(Twoot("2", USER_ID, TWOOT_2, position2)) }
        // https://github.com/mockk/mockk/issues/6#issuecomment-346479985
        /*
        If I understand it correctly, verifyAll can be used to check that no more interactions were made for the mocked class
         */
    }


    test("$name should get twoots from position") {
        val id = "1"

        add(id, TWOOT)

        val result = repository.get(id)
        result shouldNotBe null
        result?.id shouldBe id
        result?.senderId shouldBe USER_ID
        result?.content shouldBe TWOOT
    }

    test("$name should delete twoots from position") {
        val id = "1"

        val twoot = repository.add(id, USER_ID, TWOOT)

        repository.delete(twoot)

        val result = repository.get(id)
        result shouldBe null
    }

    test("$name should only load twoots from followed users") {
        add("1", TWOOT)

        every { callback.accept(any()) } just Runs

        repository.query(twootQuery.lastSeenPosition(Position.INITIAL_POSITION), callback)
    }

    afterEach {
        println("Clearing")
        repository.clear()
    }

}

private fun add(id: String, content: String): Position {
    val twoot = repository.add(id, USER_ID, content)
    USER_ID shouldBe twoot.senderId
    content shouldBe twoot.content
    return twoot.position
}
