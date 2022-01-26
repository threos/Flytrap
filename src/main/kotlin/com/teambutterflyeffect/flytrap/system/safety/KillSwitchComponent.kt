package com.teambutterflyeffect.flytrap.system.safety

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext

class KillSwitchComponent(context: LifecycleContext) : IKillSwitchComponent(context) {
    override fun activate() {
        KILL_SWITCH_STATE.data = KillSwitchState.ACTIVE
    }

    override fun onTick(context: ObjectContext<*>) { }
}