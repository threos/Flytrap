package com.teambutterflyeffect.flytrap.component.debugserver

import com.fasterxml.jackson.module.kotlin.readValue
import com.teambutterflyeffect.flytrap.component.flylogger.LogLevel
import com.teambutterflyeffect.flytrap.component.flylogger.api.LogModel
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.component.flylogger.storage.LogStorage
import com.teambutterflyeffect.flytrap.network.MultimethodServer
import com.teambutterflyeffect.flytrap.system.data.mapper
import com.teambutterflyeffect.flytrap.system.error.ErrorCode
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents
import com.teambutterflyeffect.flytrap.system.safety.KillSwitchComponent
import com.teambutterflyeffect.flytrap.system.safety.KillSwitchMessage
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

const val port = 9319

class DebugServer(context: LifecycleContext) :
    MultimethodServer(context, port) {

    override val TAG = "DebugServer"

    override fun routing(context: ObjectContext<*>, routing: Routing): Unit = routing.run {
        get("/kill") {
            log(TAG, "Trigger kill switch", level = LogLevel.VERBOSE)
            this@DebugServer.context.post(KillSwitchMessage(Intents.create(context, null), ErrorCode.REMOTE, reason = try {"Kill switch triggered via DebugServer by: ${call.request.origin.host} over port: $port" } catch (e: Throwable) {"Kill switch triggered via DebugServer over port: $port"}))
            call.respondText(LogStorage.data.messages.joinToString(separator = "\n"))
        }
        post("/kill") {
            val model: LogModel = mapper.readValue(call.receiveText())

            log(tag = model.tag, message = model.message, level = model.level)

            call.respondText(status = HttpStatusCode.OK, text = "OK")
        }
    }

}