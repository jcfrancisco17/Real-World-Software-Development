package com.iteratrlearning.shu_book.kotlin.chapter_06

object TestData {

    const val USER_ID = "Joe"
    const val OTHER_USED_ID = "John"
    const val NOT_A_USER = "Jack"

    const val TWOOT = "Hello World!"
    const val TWOOT_2 = "Bye World!"

    const val PASSWORD = "ahc5ez"
    val SALT = KeyGenerator.newSalt()
    val PASSWORD_BYTES = KeyGenerator.hash(PASSWORD, SALT)

    fun twootAt(id: String, position: Position): Twoot {
        return Twoot(id, OTHER_USED_ID, TWOOT, position)
    }
}
