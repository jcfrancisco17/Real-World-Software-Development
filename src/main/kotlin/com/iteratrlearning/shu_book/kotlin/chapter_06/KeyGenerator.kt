package com.iteratrlearning.shu_book.kotlin.chapter_06

import org.bouncycastle.crypto.generators.SCrypt
import java.security.SecureRandom
import kotlin.text.Charsets.UTF_16


object KeyGenerator {

    private val SCRYPT_COST = 16384
    private val SCRYPT_BLOCK_SIZE = 8
    private val SCRYPT_PARALLELISM = 1
    private val KEY_LENGTH = 20
    private val SALT_LENGTH = 16

    private val secureRandom = SecureRandom()

    fun hash(password: String, salt: ByteArray): ByteArray {
        val passwordBytes = password.toByteArray(UTF_16)
        return SCrypt.generate(
            passwordBytes,
            salt,
            SCRYPT_COST,
            SCRYPT_BLOCK_SIZE,
            SCRYPT_PARALLELISM,
            KEY_LENGTH
        )
    }

    fun newSalt(): ByteArray {
        val salt = ByteArray(SALT_LENGTH)
        secureRandom.nextBytes(salt)
        return salt
    }
}
