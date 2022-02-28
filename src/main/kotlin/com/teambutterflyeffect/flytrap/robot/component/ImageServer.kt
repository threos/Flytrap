package com.teambutterflyeffect.flytrap.robot.component

import com.teambutterflyeffect.flytrap.network.MultimethodServer
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.nio.file.Files.readAllBytes
import java.util.*

var imageData: InputStream? = null
var onUpdate: (() -> Unit)? = null

class ImageServer(
    context: LifecycleContext,
) : MultimethodServer(context, "8034", hostOverride = "10.80.34.170", deviceNumber = 12, port = 5805) {
    var i = 0
    override fun routing(context: ObjectContext<*>, routing: Routing): Unit = routing.run {
        post("/") {
            println("Post")
            try {
                withContext(Dispatchers.IO) {
                    val bytes = call.receiveStream().readAllBytes()
                    imageData = ByteArrayInputStream(bytes)
                    onUpdate?.invoke()
                    if(i % 20 == 0) File("/Users/erayeminocak/IdeaProjects/Flytrap/timg3/img-${UUID.randomUUID()}.jpg").writeBytes(bytes)
                    i++
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}