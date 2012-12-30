package org.vertx.kotlin.core

import org.vertx.java.core.json.JsonObject
import java.util.ArrayList
import java.util.HashMap

fun configuration(vararg pairs: Pair<String, Any>): JsonObject {
    var map: HashMap<String?, Any?> = HashMap()
    for((key, value) in pairs){
        map.put(key, value)
    }

    return JsonObject(map)
}


