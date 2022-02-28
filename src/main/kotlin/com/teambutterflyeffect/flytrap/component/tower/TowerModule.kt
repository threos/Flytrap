package com.teambutterflyeffect.flytrap.component.tower

import com.teambutterflyeffect.flytrap.component.tower.component.TowerMotorComponent
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference

class TowerModule(context: LifecycleContext) : LifecycleObject(context) {
    override fun onTick(context: ObjectContext<*>) {}

    override fun components(): Array<ObjectReference<out LifecycleObject>> = arrayOf(
        ObjectReference(TowerMotorComponent::class.java)
    )
}