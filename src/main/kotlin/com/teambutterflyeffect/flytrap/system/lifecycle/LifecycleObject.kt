package com.teambutterflyeffect.flytrap.system.lifecycle

import com.teambutterflyeffect.flytrap.system.execution.Ticker

abstract class LifecycleObject(context: LifecycleContext): Ticker {
    fun onCreate(context: ObjectContext<*>) {}

    fun onStart(context: ObjectContext<*>) {}

    fun onStop(context: ObjectContext<*>) {}

    fun onDestroy(context: ObjectContext<*>) {}
}