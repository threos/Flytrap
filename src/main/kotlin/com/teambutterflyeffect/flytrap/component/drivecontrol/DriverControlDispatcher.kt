package com.teambutterflyeffect.flytrap.component.drivecontrol

import com.teambutterflyeffect.flytrap.control.input.message.InputChannelMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.MessageDispatcherObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage

class DriverControlDispatcher(context: LifecycleContext) : LifecycleObject(context) {
    override fun onTick(context: ObjectContext<*>) {
        TODO("Not yet implemented")
    }



    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(InputChannelMessage::class.java)

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
    }
}