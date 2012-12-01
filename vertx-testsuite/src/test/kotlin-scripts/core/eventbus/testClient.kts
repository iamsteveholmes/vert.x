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

import org.vertx.java.framework.TestUtils

tu.checkContext()

// Most testing occurs in the Java tests

eb = vertx.eventBus
address = 'foo-address'

sent = [
  price : 23.45,
  name : 'tim'
]

emptySent = [
  address : address
]

reply = [
  desc: "approved",
  status: 123
]

def assertSent(msg) {
  tu.azzert(sent['price'] == msg['price'])
  tu.azzert(sent['name'] == msg['name'])
}


def assertReply(rep) {
  tu.azzert(reply['desc'] == rep['desc'])
  tu.azzert(reply['status'] == rep['status'])
}

def testSimple() {

  def handled = false
  eb.registerHandler(address, myHandler = { msg ->
    tu.checkContext()
    tu.azzert(!handled)
    assertSent(msg.body)
    eb.unregisterHandler(address, myHandler)
    handled = true
    tu.testComplete()
  })

  eb.send(address, sent)
}

def testEmptyMessage() {

  def handled = false
  eb.registerHandler(address, myHandler = { msg ->
    tu.checkContext()
    tu.azzert(!handled)
    eb.unregisterHandler(address, myHandler)
    handled = true
    tu.testComplete()
  })

  eb.send(address, emptySent)
}


def testUnregister() {

  def handled = false
  eb.registerHandler(address, myHandler = { msg ->
    tu.checkContext()
    tu.azzert(!handled)
    assertSent(msg.body)
    eb.unregisterHandler(address, myHandler)
    // Unregister again - should do nothing
    eb.unregisterHandler(address, myHandler)
    handled = true
    // Wait a little while to allow any other messages to arrive
    vertx.setTimer(100, {
      tu.testComplete()
    })
  })

  2.times {
    eb.send(address, sent)
  }
}

def testWithReply() {

  def handled = false
  eb.registerHandler(address, myHandler = { msg ->
    tu.checkContext()
    tu.azzert(!handled)
    assertSent(msg.body)
    eb.unregisterHandler(address, myHandler)
    handled = true
    msg.reply(reply)
  })

  eb.send(address, sent, { reply ->
    tu.checkContext()
    assertReply(reply.body)
    tu.testComplete()
  })
}

def testReplyOfReplyOfReply() {

  eb.registerHandler(address, myHandler = { msg ->
    tu.azzert("message" == msg.body)
    msg.reply("reply", { reply ->
      tu.azzert("reply-of-reply" == reply.body)
      reply.reply("reply-of-reply-of-reply")
      eb.unregisterHandler(address, myHandler)
    })
  })

  eb.send(address, "message", { reply->
    tu.azzert("reply" == reply.body)
    reply.reply("reply-of-reply", { replyReply ->
      tu.azzert("reply-of-reply-of-reply" == replyReply.body)
      tu.testComplete()
    })
  })
}

def testEmptyReply() {

  def handled = false
  eb.registerHandler(address, myHandler = { msg ->
    tu.checkContext()
    tu.azzert(!handled)
    assertSent(msg.body)
    eb.unregisterHandler(address, myHandler)
    handled = true
    msg.reply([:])
  })

  eb.send(address, sent, { reply ->
    tu.checkContext()
    tu.testComplete()
  })
  eb.send(address, sent)
}

def testEchoString() {
  echo("foo")
}

def testEchoNumber1() {
  echo(1234)
}

def testEchoNumber2() {
  echo(1.2345)
}

def testEchoBooleanTrue() {
  echo(true)
}

def testEchoBooleanFalse() {
  echo(false)
}

def testEchoJson() {
  echo(sent)
}

def testEchoNull() {
  echo(null)
}


def echo(msg) {
  eb.registerHandler(address, myHandler = { received ->
    tu.checkContext()
    eb.unregisterHandler(address, myHandler)
    received.reply(received.body)
  })
  eb.send(address, msg, { reply ->

    if (msg != null) {
      if (msg instanceof Map == false) {
        tu.azzert(msg == reply.body)
      } else {
        tu.azzert(msg.equals(reply.body))
      }
    } else {
      tu.azzert(reply.body == null)
    }

    tu.testComplete()
  })
}


tu.registerTests(this)
tu.appReady()

void vertxStop() {
  tu.unregisterAll()
  tu.appStopped()
}

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

