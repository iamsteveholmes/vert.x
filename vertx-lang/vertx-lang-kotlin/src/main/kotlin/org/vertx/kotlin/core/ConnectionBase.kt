package org.vertx.kotlin.core

import org.vertx.java.core.net.impl.ConnectionBase
import org.vertx.java.core.Handler

public fun ConnectionBase.closedHandler(handler: ()->Any?) {
    this.closedHandler (handler(handler))
}