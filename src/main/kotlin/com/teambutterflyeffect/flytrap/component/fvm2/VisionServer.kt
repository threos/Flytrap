package com.teambutterflyeffect.flytrap.component.fvm2

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.module.kotlin.readValue
import com.teambutterflyeffect.flytrap.component.flylogger.LogLevel
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.HubDataMessage
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.HubEntity
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

    var i1: Int = 0
    var i2: Int = 0
    override val TAG = "VisionServer"

    private val reader: ObjectReader = mapper.readerFor(object : TypeReference<List<VisionEntity>>() {})
    private val hubReader: ObjectReader = mapper.readerFor(object : TypeReference<HubEntity>() {})


    override fun routing(context: ObjectContext<*>, routing: Routing): Unit = routing.run {
        post {
            try {
                val text = call.receiveText()
                val entities = reader.readValue<List<VisionEntity>>(text)

                if (i1 % 50 == 0) log(
                    TAG,
                    "Receive vision packet ${this@VisionServer.i1}: $text",
                    level = LogLevel.VERBOSE
                )

                this@VisionServer.context.post(
                    VisionDataMessage(
                        entities.mapNotNull {
                            if (it.probability >= 0.7) {
                                VisionEntity(
                                    it.id,
                                    it.probability,
                                    it.x_0 / 576,
                                    it.y_0 / 176,
                                    it.x_1 / 576,
                                    it.y_1 / 176
                                )
                            } else null
                        },
                        Intents.create(context, null),
                    )
                )
                i1++
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
        post("/hub") {
            try {
                val text = call.receiveText()

                if (i2 % 50 == 0) log(TAG, "Receive hub packet ${this@VisionServer.i2}: $text")

                val entity = hubReader.readValue<HubEntity>(text)

                //log(TAG, "Post hub message")
                this@VisionServer.context.post(
                    HubDataMessage(
                        Intents.create(context, null),
                        entity
                    )
                )
                i2++
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }
}