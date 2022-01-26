package com.teambutterflyeffect.flytrap.base

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleProvider

fun runFlytrap(robotClass: Class<out FlytrapRobot>) {
    val flytrapInstance = FlytrapInstance()

    val robot = robotClass.getDeclaredConstructor().newInstance(flytrapInstance.lifecycleProvider)
}

class FlytrapInstance {
    val lifecycleProvider = LifecycleProvider()
}

abstract class FlytrapRobot(context: LifecycleContext) : LifecycleObject(context)