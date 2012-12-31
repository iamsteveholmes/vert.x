package org.vertx.kotlin.core

import org.vertx.java.deploy.Container
import org.vertx.java.core.json.JsonObject

public fun Container.deployModule(main: String, config: JsonObject = JsonObject(), instances: Int = 1, doneHandler: ((String?)->Any?)? = null) {
    deployModule(main, config, instances, if(doneHandler!=null) handler(doneHandler) else null)
}

public fun Container.deployVerticle(main: java.lang.Class<*>, config: JsonObject = JsonObject(), instances: Int = 1, doneHandler: ((String?)->Any?)? = null) {
    deployModule(main.getName(), config, instances, if(doneHandler!=null) handler(doneHandler) else null)
}

