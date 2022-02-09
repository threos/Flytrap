package com.teambutterflyeffect.flytrap.component.flylogger.api

import com.fasterxml.jackson.module.kotlin.readValue
import com.teambutterflyeffect.flytrap.component.flylogger.LogLevel
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.component.flylogger.storage.LogStorage
import com.teambutterflyeffect.flytrap.network.MultimethodServer
import com.teambutterflyeffect.flytrap.system.data.mapper
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

const val port = 2542

class LogServer(context: LifecycleContext) :
    MultimethodServer(context, port) {

    override val TAG = "LogServer"

    override fun routing(context: ObjectContext<*>, routing: Routing): Unit = routing.run {
        get("/log") {
            log(TAG, "Requested full log dump", level = LogLevel.VERBOSE)
            call.respondText(LogStorage.data.messages.joinToString(separator = "\n"))
        }
        post("/log") {
            val model: LogModel = mapper.readValue(call.receiveText())

            log(tag = model.tag, message = model.message, level = model.level)

            call.respondText(status = HttpStatusCode.OK, text = "OK")
        }
    }

}