package com.teambutterflyeffect.flytrap.component.debugserver

import com.fasterxml.jackson.core.type.TypeReference
import com.teambutterflyeffect.flytrap.component.flylogger.LogLevel
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.HubEntity
import com.teambutterflyeffect.flytrap.network.MultimethodServer
import com.teambutterflyeffect.flytrap.system.data.mapper
import com.teambutterflyeffect.flytrap.system.error.ErrorCode
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents
import com.teambutterflyeffect.flytrap.system.safety.KillSwitchMessage
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

const val port = 5807

class DebugServer(context: LifecycleContext) :
    MultimethodServer(context, port = port, teamNumber = "8034") {

    val reader = mapper.readerFor(object : TypeReference<RobotConfiguration>() {})
    val writer = mapper.writerFor(object : TypeReference<RobotConfiguration>() {})


    override val TAG = "DebugServer"

    override fun routing(context: ObjectContext<*>, routing: Routing): Unit = routing.run {
        post("/kill") {
            log(TAG, "Trigger kill switch", level = LogLevel.INFO)
            this@DebugServer.context.post(
                KillSwitchMessage(
                    Intents.create(context, null),
                    ErrorCode.REMOTE,
                    reason = try {
                        "Kill switch triggered via DebugServer by: ${call.request.origin.host} over port: $port"
                    } catch (e: Throwable) {
                        e.printStackTrace()
                        "Kill switch triggered via DebugServer over port: $port"
                    }
                )
            )
            call.respondText(status = HttpStatusCode.OK, text = "OK")
        }
        get("/info") {
            call.respondText(status = HttpStatusCode.OK, text = "OK")
        }
        post("/configuration") {
            log(TAG, "Set configuration", level = LogLevel.INFO)

            ROBOT_CONFIGURATION = reader.readValue(call.receiveText())

            call.respondText(status = HttpStatusCode.OK, text = writer.writeValueAsString(ROBOT_CONFIGURATION))
        }
        post("/setRpm") {
            val text = call.receiveText()
            log(TAG, "Set rpm: $text", level = LogLevel.INFO)
            ROBOT_CONFIGURATION.BALLER_RPM = text.toInt()
            call.respondText(status = HttpStatusCode.OK, text = "OK")
        }
        post("/setLowRpm") {
            val text = call.receiveText()
            log(TAG, "Set low rpm: $text", level = LogLevel.INFO)
            ROBOT_CONFIGURATION.BALLER_LOW_RPM = text.toInt()
            call.respondText(status = HttpStatusCode.OK, text = "OK")
        }
        post("/setTArea") {
            val text = call.receiveText()
            log(TAG, "Set rpm: $text", level = LogLevel.INFO)
            call.respondText(status = HttpStatusCode.OK, text = "OK")
        }
        get("/configuration") {
            log(TAG, "Get configuration", level = LogLevel.INFO)
            call.respondText(status = HttpStatusCode.OK, text = writer.writeValueAsString(ROBOT_CONFIGURATION))
        }
    }

}