/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//package core.buffer

import org.vertx.java.framework.TestUtils
import org.vertx.kotlin.core.*
import org.vertx.java.core.buffer.Buffer
import org.vertx.java.core.Vertx
import org.vertx.java.core.impl.VertxInternal
import org.vertx.java.core.impl.DefaultVertx
import org.vertx.java.deploy.impl.VertxLocator
import java.lang.reflect.Method

val testUtils = TestUtils(vertx)

verticle.onStop = {
    testUtils.unregisterAll()
    testUtils.appStopped()
}

verticle.onStart = {
    testUtils.registerTests(object  {
        fun testPutAtGetAtByte() {
            val buffer = Buffer()
            val b: Byte = 12
            buffer.setByte(10, b)
            testUtils.azzert(buffer.getByte(10) == b)
            testUtils.testComplete()
        }

        fun testPutAtInt() {
            val buffer = Buffer()
            val i = 1233
            buffer.setInt(10, i)
            testUtils.azzert(buffer.getInt(10) == i)
            testUtils.testComplete()
        }

        fun testPutAtLong() {
            val buffer = Buffer()
            val l: Long = 1233
            buffer.setLong(10, l)
            testUtils.azzert(buffer.getLong(10) == l)
            testUtils.testComplete()
        }

        fun testPutAtShort() {
            val buffer = Buffer()
            val s: Short = 123
            buffer.setShort(10, s)
            testUtils.azzert(buffer.getShort(10) == s)
            testUtils.testComplete()
        }

        fun testPutAtDouble() {
            val buffer = Buffer()
            val d: Double = 123.123
            buffer.setDouble(10, d)
            testUtils.azzert(buffer.getDouble(10) == d)
            testUtils.testComplete()
        }

        fun testPutAtFloat() {
            val buffer = Buffer()
            val f: Float = 123.123.toFloat()
            buffer.setFloat(10, f)
            testUtils.azzert(buffer.getFloat(10) == f)
            testUtils.testComplete()
        }

        fun testPutAtBuffer() {
            val buffer = Buffer()
            val b = TestUtils.generateRandomBuffer(10)
            buffer.setBuffer(10, b)
            testUtils.azzert(TestUtils.buffersEqual(buffer.getBuffer(10, 20), b))
            testUtils.testComplete()
        }
    })
    testUtils.appReady()
}
