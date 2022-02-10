package com.teambutterflyeffect.flytrap.component.driverassist

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext

class DriverAssistModule(context: LifecycleContext) : LifecycleObject(context) {
    override fun onTick(context: ObjectContext<*>) {}

    override fun onCreate(context: ObjectContext<*>) {
        super.onCreate(context)

    }
}