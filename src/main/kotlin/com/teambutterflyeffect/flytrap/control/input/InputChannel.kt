package com.teambutterflyeffect.flytrap.control.input

import com.teambutterflyeffect.flytrap.system.execution.Ticker
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ExternalReference

class InputChannelReference(objectClass: Class<InputChannel>): ExternalReference<InputChannel>(
    objectClass
)

abstract class InputChannel(val id: Int, initialData: Double? = null): Ticker {
    open var data: Double = 0.0

    init {
        initialData?.let {
            data = it
        }
    }

    abstract override fun onTick(context: ObjectContext<*>)
}

open class StaticInputChannel(val id: Int, val data: Double)