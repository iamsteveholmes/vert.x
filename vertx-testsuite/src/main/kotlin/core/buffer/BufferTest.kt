package core.buffer

import org.vertx.java.core.buffer.Buffer
import org.vertx.kotlin.core.*
import org.vertx.java.framework.TestUtils

class BufferTest(val tu: TestUtils) {
    fun testPutAtGetAtByte() {
      val buffer = Buffer()
      val b : Byte = 12
      buffer.setByte(10, b)
      tu.azzert(buffer.getByte(10) == b)
      tu.testComplete()
    }

    fun testPutAtInt() {
      val buffer = Buffer()
      val i = 1233
      buffer.setInt(10, i)
      tu.azzert(buffer.getInt(10) == i)
      tu.testComplete()
    }

    fun testPutAtLong() {
      val buffer = Buffer()
      val l : Long = 1233
      buffer.setLong(10, l)
      tu.azzert(buffer.getLong(10) == l)
      tu.testComplete()
    }

    fun testPutAtShort() {
      val buffer = Buffer()
      val s: Short = 123
      buffer.setShort(10, s)
      tu.azzert(buffer.getShort(10) == s)
      tu.testComplete()
    }

    fun testPutAtDouble() {
      val buffer = Buffer()
      val d: Double = 123.123
      buffer.setDouble(10, d)
      tu.azzert(buffer.getDouble(10) == d)
      tu.testComplete()
    }

    fun testPutAtFloat() {
      val buffer = Buffer()
      val f: Float = 123.123.toFloat()
      buffer.setFloat(10, f)
      tu.azzert(buffer.getFloat(10) == f)
      tu.testComplete()
    }

    fun testPutAtBuffer() {
      val buffer = Buffer()
      val b = TestUtils.generateRandomBuffer(10)
      buffer.setBuffer(10, b)
      tu.azzert(TestUtils.buffersEqual(buffer.getBuffer(10, 20), b))
      tu.testComplete()
    }

    fun vertxStop(){
        tu.unregisterAll()
        tu.appStopped()
    }

    /*fun run(){

    }*/
}