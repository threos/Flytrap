package com.teambutterflyeffect.flytrap.component.debugserver

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import edu.wpi.first.wpilibj.DriverStation

class RobotConfigurationComponent(context: LifecycleContext) : LifecycleObject(context) {
    override fun onTick(context: ObjectContext<*>) {
        ROBOT_CONFIGURATION.alliance = DriverStation.getAlliance()
    }
}