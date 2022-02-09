package com.teambutterflyeffect.flytrap.system.safety

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage

class KillSwitchComponent(context: LifecycleContext) : IKillSwitchComponent(context) {
    override fun activate() {
        UniversalKillSwitch.activate()
    }

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
        if(message is KillSwitchMessage) {
            activate()
        }
    }

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(KillSwitchMessage::class.java)

    override fun onTick(context: ObjectContext<*>) { }
}