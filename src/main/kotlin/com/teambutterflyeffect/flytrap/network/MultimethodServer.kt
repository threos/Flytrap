package com.teambutterflyeffect.flytrap.network

import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

abstract class MultimethodServer(context: LifecycleContext, private val port: Int, private val gracePeriodMillis: Long = 5000, private val destroyTimeout: Long = 15000) : LifecycleObject(context) {
    open val TAG = "MultimethodServer"

    override fun onTick(context: ObjectContext<*>) {}

    lateinit var engine: ApplicationEngine

    override fun onCreate(context: ObjectContext<*>) {
        super.onCreate(context)

        engine = embeddedServer(Netty, port = port) {
            log(TAG, "Configure embedded server on port: $port")
            configure(this)
            routing {
                log(TAG, "Set embedded server routing")
                routing(context, this)
            }
        }
        engine.start(wait = false)
    }

    override fun onDestroy(context: ObjectContext<*>) {
        super.onDestroy(context)
        log(TAG, "Stop embedded server on port: $port")
        engine.stop(
            gracePeriodMillis,
            destroyTimeout,
        )
    }

    open fun configure(application: Application) {}

    abstract fun routing(context: ObjectContext<*>, routing: Routing)
}