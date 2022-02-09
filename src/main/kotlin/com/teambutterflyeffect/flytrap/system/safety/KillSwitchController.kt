package com.teambutterflyeffect.flytrap.system.safety

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference

val UNIVERSAL_KILL_SWITCH = ObjectReference(KillSwitchComponent::class.java)

object KillSwitchController {
    fun attach() {
            LifecycleContext.attach(UNIVERSAL_KILL_SWITCH)
    }
}