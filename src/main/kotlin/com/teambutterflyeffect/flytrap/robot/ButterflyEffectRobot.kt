package com.teambutterflyeffect.flytrap.robot

import com.teambutterflyeffect.flytrap.base.FlytrapRobot
import com.teambutterflyeffect.flytrap.robot.component.RobotComponent
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference

class ButterflyEffectRobot(context: LifecycleContext) : FlytrapRobot(context) {
    override fun onTick(context: ObjectContext<*>) {}

    override fun robotComponent(): ObjectReference<out LifecycleObject> = ObjectReference(RobotComponent::class.java)
}