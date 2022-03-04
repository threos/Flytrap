package com.teambutterflyeffect.flytrap.component.aircontrol

import com.teambutterflyeffect.flytrap.component.aircontrol.backend.AirControlStationBackend
import com.teambutterflyeffect.flytrap.component.aircontrol.gui.DisplayComponent
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference

class AirControlModule(context: LifecycleContext) : LifecycleObject(context) {
    override fun onTick(context: ObjectContext<*>) {}

    override fun components(): Array<ObjectReference<out LifecycleObject>> = arrayOf(
        ObjectReference(DisplayComponent::class.java),
        ObjectReference(AirControlStationBackend::class.java),
    )
}