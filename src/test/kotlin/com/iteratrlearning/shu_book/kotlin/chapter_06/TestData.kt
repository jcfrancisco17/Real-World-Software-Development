package com.iteratrlearning.shu_book.kotlin.chapter_06

object TestData {

    val USER_ID = "Joe"
    val OTHER_USED_ID = "John"
    val NOT_A_USER = "Jack"

    val TWOOT = "Hello World!"
    val TWOOT_2 = "Bye World!"

    val SALT = KeyGenerator.newSalt()
    val PASSWORD = "ahc5ez"
    val PASSWORD_BYTES = KeyGenerator.hash(PASSWORD, SALT)

    fun twootAt(id: String, position: Position): Twoot {
        return Twoot(id, OTHER_USED_ID, TWOOT, position)
    }
}
