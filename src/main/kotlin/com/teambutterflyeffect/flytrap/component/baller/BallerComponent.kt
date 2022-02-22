package com.teambutterflyeffect.flytrap.component.baller

import com.teambutterflyeffect.flytrap.component.baller.shooter.ShooterComponent
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.BallerTargetMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference

class BallerComponent(context: LifecycleContext) : LifecycleObject(context) {
    var distance: Float? = null
    override fun onTick(context: ObjectContext<*>) {}

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
        if(message is BallerTargetMessage && message.isValid()) {

        }
    }

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(
        BallerTargetMessage::class.java
    )

    override fun components(): Array<ObjectReference<out LifecycleObject>> = arrayOf(
        ObjectReference(ShooterComponent::class.java)
    )
}