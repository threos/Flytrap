package com.teambutterflyeffect.flytrap.base

import com.teambutterflyeffect.flytrap.component.flylogger.LogLevel
import com.teambutterflyeffect.flytrap.component.flylogger.log
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
    private val TAG = "DefaultRobotObject"
    override fun onTick(context: ObjectContext<*>) {}

    override fun onCreate(context: ObjectContext<*>) {
        super.onCreate(context)
        log(TAG, "Default robot object created. You need to use proper components to build a functional robot. Default object won't perform any operations.", level = LogLevel.WARNING)
    }
}

class Robot(context: LifecycleContext) : FlytrapRobot(context) {
    override fun onTick(context: ObjectContext<*>) {}

    override fun autonomousComponent(): ObjectReference<out LifecycleObject> = ObjectReference(DefaultRobotObject::class.java)

    override fun teleopComponent(): ObjectReference<out LifecycleObject> = ObjectReference(DefaultRobotObject::class.java)
}