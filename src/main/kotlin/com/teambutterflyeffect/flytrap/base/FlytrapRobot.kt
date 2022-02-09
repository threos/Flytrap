package com.teambutterflyeffect.flytrap.base

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleProvider
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference
import com.teambutterflyeffect.flytrap.system.wpilink.WPILinker
import edu.wpi.first.wpilibj.RobotBase
import edu.wpi.first.wpilibj.TimedRobot

fun runFlytrapRobot(robotClass: Class<out FlytrapRobot>) {
    val flytrapInstance = FlytrapInstance()

    WPILinker.launchWPIRobot(
        robotClass.getDeclaredConstructor(
            LifecycleContext::class.java
        ).newInstance(flytrapInstance.lifecycleProvider.context)
    )
}

class FlytrapInstance {
    val lifecycleProvider = LifecycleProvider()
}

abstract class FlytrapRobot(context: LifecycleContext) : LifecycleObject(context) {
    enum class RobotMode {
        ROBOT,
        AUTONOMOUS,
        TELEOP,
        TEST,
        SIMULATION,
    }

    enum class RobotEvent {
        INIT,
        EXIT,
    }

    open fun robotComponent(): ObjectReference<out LifecycleObject> = ObjectReference(DefaultRobotObject::class.java)

    open fun autonomousComponent(): ObjectReference<out LifecycleObject> = ObjectReference(DefaultRobotObject::class.java)

    open fun teleopComponent(): ObjectReference<out LifecycleObject> = ObjectReference(DefaultRobotObject::class.java)

    open fun testComponent(): ObjectReference<out LifecycleObject> = ObjectReference(DefaultRobotObject::class.java)

    open fun simulationComponent(): ObjectReference<out LifecycleObject> = ObjectReference(DefaultRobotObject::class.java)
}

class DefaultRobotObject(context: LifecycleContext) : LifecycleObject(context) {
    override fun onTick(context: ObjectContext<*>) {}
}

class Robot(context: LifecycleContext) : FlytrapRobot(context) {
    override fun onTick(context: ObjectContext<*>) {}

    override fun autonomousComponent(): ObjectReference<out LifecycleObject> = ObjectReference(DefaultRobotObject::class.java)

    override fun teleopComponent(): ObjectReference<out LifecycleObject> = ObjectReference(DefaultRobotObject::class.java)
}