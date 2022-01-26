package com.teambutterflyeffect.flytrap.system.safety

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject

abstract class IKillSwitchComponent(context: LifecycleContext) : LifecycleObject(context) {
    abstract fun activate()
}