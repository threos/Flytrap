package com.teambutterflyeffect.flytrap.robot.component

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext

class RobotComponent(context: LifecycleContext) : LifecycleObject(context) {
    override fun onTick(context: ObjectContext<*>) {}
}