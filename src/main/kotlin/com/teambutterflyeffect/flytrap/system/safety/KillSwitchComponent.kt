package com.teambutterflyeffect.flytrap.system.safety

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext

class KillSwitchComponent(context: LifecycleContext) : IKillSwitchComponent(context) {
    override fun activate() {
        UniversalKillSwitch.activate()
    }



    override fun onTick(context: ObjectContext<*>) { }
}