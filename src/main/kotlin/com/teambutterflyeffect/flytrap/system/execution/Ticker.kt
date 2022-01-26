package com.teambutterflyeffect.flytrap.system.execution

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext

interface Ticker {
    fun onTick(context: ObjectContext<*>)
}