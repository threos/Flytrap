package com.teambutterflyeffect.flytrap.control.input

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject

abstract class BaseInput<I: InputChannel>(context: LifecycleContext) : LifecycleObject(context) {
    open fun getInputChannels() : List<I> = emptyList()

    open fun getChannel(id: Int): I {
        TODO("implement getChannel")
    }

    open fun getName(): String = "BaseInput"

    override fun toString(): String {
        return getName()
    }
}