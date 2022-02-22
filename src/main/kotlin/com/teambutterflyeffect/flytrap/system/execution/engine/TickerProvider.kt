package com.teambutterflyeffect.flytrap.system.execution.engine

import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectParent

class TickerProvider {
    val tag = "TickerProvider"

    @Volatile var tickId =  -1
    val objects: Collection<ObjectParent<out LifecycleObject>> = LifecycleContext.getObjects()

    fun hit() {
        tickId++
    }

    val task: EngineTask = {
        log(tag, "Create engine task")
        var tickId = -1

        while (!Thread.interrupted()) {
            if(tickId == this@TickerProvider.tickId) {
                //log(tag, "Abort tick")
                continue
            }

            objects.parallelStream().forEach {
                it.tick()
            }

            tickId = this@TickerProvider.tickId
        }
    }
}