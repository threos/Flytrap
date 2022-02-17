package com.teambutterflyeffect.flytrap.component.fvm2

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectReader
import com.teambutterflyeffect.flytrap.component.flylogger.LogLevel
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.VisionDataMessage
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.VisionEntity
import com.teambutterflyeffect.flytrap.network.MultimethodServer
import com.teambutterflyeffect.flytrap.system.data.mapper
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*

const val port = 5801

class VisionServer(
    context: LifecycleContext,
) : MultimethodServer(context, port = port, gracePeriodMillis = 1000, destroyTimeout = 50000, teamNumber = "8034") {

    override val TAG = "VisionServer"

    private val reader: ObjectReader = mapper.readerFor(object : TypeReference<List<VisionEntity>>() {})

    override fun routing(context: ObjectContext<*>, routing: Routing): Unit = routing.run {
        post {
            val text = call.receiveText()
            val entities = reader.readValue<List<VisionEntity>>(text)

            log(TAG, "Receive new vision packet: $text", level = LogLevel.VERBOSE)
            this@VisionServer.context.post(
                VisionDataMessage(
                    entities.map {
                                 VisionEntity(it.id, it.probability, it.x_0 / 300, it.y_0 / 200, it.x_1 / 300, it.y_1 / 200)
                    },
                    Intents.create(context, null)
                )
            )
        }
    }
}