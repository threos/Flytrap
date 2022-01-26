package com.teambutterflyeffect.flytrap.control.input

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject

abstract class BaseInput<I: InputChannel>(context: LifecycleContext) : LifecycleObject(context) {
    fun getInputChannels() : List<I> = emptyList()

    fun getChannel(id: Int): I {
        TODO("implement getChannel")
    }
}