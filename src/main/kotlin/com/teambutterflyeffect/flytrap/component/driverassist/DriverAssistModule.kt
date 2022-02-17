package com.teambutterflyeffect.flytrap.component.driverassist

import com.teambutterflyeffect.flytrap.component.driverassist.targetgravity.TargetGravityComponent
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference

class DriverAssistModule(context: LifecycleContext) : LifecycleObject(context) {
    override fun onTick(context: ObjectContext<*>) {}

    override fun components(): Array<ObjectReference<out LifecycleObject>> = arrayOf(ObjectReference(TargetGravityComponent::class.java))
}