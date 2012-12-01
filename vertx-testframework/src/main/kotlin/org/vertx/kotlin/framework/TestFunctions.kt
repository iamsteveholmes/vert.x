package org.vertx.kotlin.framework

import org.vertx.java.core.buffer.Buffer
import org.vertx.java.framework.TestUtils as TU

/*public fun generateRandomBuffer(length: Int, avoid: Boolean = false, avoidByte: Byte = Byte()): Buffer? {
    return TU.generateRandomBuffer(length, avoid, avoidByte)
}

public fun generateRandomByteArray(length: Int, avoid: Boolean = false, avoidByte: Byte = Byte()): ByteArray? {
    return TU.generateRandomByteArray(length, avoid, avoidByte)
}

public fun randomUnicodeString(length: Int): String? {
    return TU.randomUnicodeString(length)
}

public fun randomAlphaString(length: Int): String ? {
    return TU.randomUnicodeString(length)
}

public fun buffersEqual(b1: Buffer, b2: Buffer): Boolean? {
    return TU.buffersEqual(b1, b2)
}*/

public fun byteArraysEqual(b1: ByteArray, b2: ByteArray): Boolean? {
    return TU.byteArraysEqual(b1, b2)
}