package com.teambutterflyeffect.flytrap.system.wpilink

import com.teambutterflyeffect.flytrap.base.FlytrapRobot
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference
import edu.wpi.first.wpilibj.RobotBase
import edu.wpi.first.wpilibj.TimedRobot
import kotlinx.coroutines.flow.merge
import java.util.*
import java.util.concurrent.locks.ReentrantLock

object WPILinker {
    fun launchWPIRobot(robot: FlytrapRobot) {
        RobotBase.startRobot {
            WPILinkRobot(robot)
        }
    }
}

class WPILinkRobot(private val flytrapRobot: FlytrapRobot) : TimedRobot(0.02) {
    val TAG = "WPILinkRobot"
    val components = IdentityHashMap<FlytrapRobot.RobotMode, ObjectReference<out LifecycleObject>>()
    var messageDispatcherCalled = 0
    var messageDispatcherDone = 0
    override fun disabledInit() {
        super.disabledInit()
        FlytrapRobot.RobotMode.values().forEach {
            if(it != FlytrapRobot.RobotMode.ROBOT) emit(it, FlytrapRobot.RobotEvent.EXIT)
        }
    }

    override fun robotInit() {
        emit(FlytrapRobot.RobotMode.ROBOT, FlytrapRobot.RobotEvent.INIT)
        addPeriodic({
            val index = messageDispatcherCalled + 1
            messageDispatcherCalled = index
            synchronized(LifecycleContext.messageLock) {
                if(messageDispatcherDone > index) return@addPeriodic
                val clone = LifecycleContext.messageQueue.toMutableSet()
                clone.forEach {
                    LifecycleContext.dispatch(it)
                }
                LifecycleContext.messageQueue.removeAll(clone)
            }
            messageDispatcherDone = messageDispatcherCalled

        }, 0.02)
    }

    override fun robotPeriodic() {
        super.robotPeriodic()
        flytrapRobot.context.periodTick()
    }

    override fun simulationInit() = emit(FlytrapRobot.RobotMode.SIMULATION, FlytrapRobot.RobotEvent.INIT)

    override fun teleopInit() = emit(FlytrapRobot.RobotMode.TELEOP, FlytrapRobot.RobotEvent.INIT)
    override fun teleopExit() = emit(FlytrapRobot.RobotMode.TELEOP, FlytrapRobot.RobotEvent.EXIT)

    override fun autonomousInit() = emit(FlytrapRobot.RobotMode.AUTONOMOUS, FlytrapRobot.RobotEvent.INIT)
    override fun autonomousExit() = emit(FlytrapRobot.RobotMode.AUTONOMOUS, FlytrapRobot.RobotEvent.EXIT)

    override fun testInit() = emit(FlytrapRobot.RobotMode.TEST, FlytrapRobot.RobotEvent.INIT)
    override fun testExit() = emit(FlytrapRobot.RobotMode.TEST, FlytrapRobot.RobotEvent.EXIT)

    override fun simulationPeriodic() {}

    override fun disabledPeriodic() {}

    override fun autonomousPeriodic() {}

    override fun teleopPeriodic() {}

    override fun testPeriodic() {}

    @Synchronized
    fun emit(mode: FlytrapRobot.RobotMode, state: FlytrapRobot.RobotEvent) {
        log(TAG, "Emit FlytrapRobot state. Mode: $mode Event: $state")
        val component = components.getOrPut(mode) {
            flytrapRobot.let {
                when (mode) {
                    FlytrapRobot.RobotMode.ROBOT -> it.robotComponent()
                    FlytrapRobot.RobotMode.SIMULATION -> it.simulationComponent()
                    FlytrapRobot.RobotMode.TELEOP -> it.teleopComponent()
                    FlytrapRobot.RobotMode.AUTONOMOUS -> it.autonomousComponent()
                    FlytrapRobot.RobotMode.TEST -> it.testComponent()
                }
            }
        }

        state.run {
            when (this) {
                FlytrapRobot.RobotEvent.INIT -> {
                    flytrapRobot.context.attach(component)
                }
                FlytrapRobot.RobotEvent.EXIT -> {
                    flytrapRobot.context.detach(component)
                }
            }
        }
    }
}