package com.teambutterflyeffect.flytrap.test.lifecycle.objects

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.data.StringMessage
import java.lang.Exception

class AntiMessageTestObject(context: LifecycleContext) : LifecycleObject(context) {

    override fun onTick(context: ObjectContext<*>) { }

    override fun onCreate(context: ObjectContext<*>) {
        LifecycleContext.subscribe(context, StringMessage::class.java)
    }

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        println("[AntiMessageTestObject] message: $message")
        throw Exception("AntiMessageTestObject received a message.")
    }
}