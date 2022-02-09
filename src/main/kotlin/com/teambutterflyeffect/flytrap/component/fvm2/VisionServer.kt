package com.teambutterflyeffect.flytrap.component.fvm2

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectReader
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

const val port = 8034

class VisionServer(
    context: LifecycleContext,
) : MultimethodServer(context, port, 15000, 30000) {

    private val reader: ObjectReader = mapper.readerFor(object : TypeReference<List<VisionEntity>>() {})

    override fun routing(context: ObjectContext<*>, routing: Routing): Unit = routing.run {
        post {
            val entities = reader.readValue<List<VisionEntity>>(call.receiveText())

            this@VisionServer.context.post(
                VisionDataMessage(
                    entities.map {
                                 VisionEntity(it.id, it.probability, it.x_0 / 600, it.y_0 / 388, it.x_1 / 600, it.y_1 / 388)
                    },
                    Intents.create(context, null)
                )
            )
        }
    }
}