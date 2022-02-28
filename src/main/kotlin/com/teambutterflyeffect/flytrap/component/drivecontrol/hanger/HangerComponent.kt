package com.teambutterflyeffect.flytrap.component.drivecontrol.hanger

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode
import com.ctre.phoenix.motorcontrol.can.VictorSPX
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage

class HangerComponent(context: LifecycleContext) : LifecycleObject(context) {
    var message: HangerMessage? = null
    val motor = VictorSPX(9)

    override fun onTick(context: ObjectContext<*>) {
        if(message?.isValid() == false) message = null

        motor.set(VictorSPXControlMode.PercentOutput, message?.content ?: 0.0)
    }

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(HangerMessage::class.java)

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
        if(message is HangerMessage && message.isValid()) {
            this@HangerComponent.message = message
        }
    }
}