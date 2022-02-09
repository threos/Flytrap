package com.teambutterflyeffect.flytrap.system.wpilink

import com.teambutterflyeffect.flytrap.base.FlytrapRobot
import edu.wpi.first.wpilibj.RobotBase
import edu.wpi.first.wpilibj.TimedRobot

object WPILinker {
    fun launchWPIRobot(robot: FlytrapRobot) {
        RobotBase.startRobot {
            WPILinkRobot(robot)
        }
    }
}

class WPILinkRobot(private val flytrapRobot: FlytrapRobot) : TimedRobot(0.02) {
    override fun disabledInit() {
        super.disabledInit()
        FlytrapRobot.RobotMode.values().forEach {
            if(it != FlytrapRobot.RobotMode.TELEOP) emit(it, FlytrapRobot.RobotEvent.EXIT)
        }
    }

    override fun robotInit() = emit(FlytrapRobot.RobotMode.TELEOP, FlytrapRobot.RobotEvent.INIT)
    override fun robotPeriodic() {
        super.robotPeriodic()
        flytrapRobot.context.periodTick()
    }



    override fun simulationInit() = emit(FlytrapRobot.RobotMode.SIMULATION, FlytrapRobot.RobotEvent.INIT)

    override fun teleopInit() = emit(FlytrapRobot.RobotMode.TELEOP, FlytrapRobot.RobotEvent.INIT)
    override fun teleopExit() = emit(FlytrapRobot.RobotMode.TELEOP, FlytrapRobot.RobotEvent.EXIT)

    override fun autonomousInit() = emit(FlytrapRobot.RobotMode.AUTONOMOUS, FlytrapRobot.RobotEvent.EXIT)
    override fun autonomousExit() = emit(FlytrapRobot.RobotMode.AUTONOMOUS, FlytrapRobot.RobotEvent.EXIT)

    override fun testInit() = emit(FlytrapRobot.RobotMode.TEST, FlytrapRobot.RobotEvent.INIT)
    override fun testExit() = emit(FlytrapRobot.RobotMode.TEST, FlytrapRobot.RobotEvent.EXIT)

    @Synchronized
    fun emit(mode: FlytrapRobot.RobotMode, state: FlytrapRobot.RobotEvent) {
        val component = flytrapRobot.let {
            when(mode) {
                FlytrapRobot.RobotMode.ROBOT -> it.robotComponent()
                FlytrapRobot.RobotMode.SIMULATION -> it.simulationComponent()
                FlytrapRobot.RobotMode.TELEOP -> it.teleopComponent()
                FlytrapRobot.RobotMode.AUTONOMOUS -> it.autonomousComponent()
                FlytrapRobot.RobotMode.TEST -> it.testComponent()
            }
        }

        state.run {
            when(this) {
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