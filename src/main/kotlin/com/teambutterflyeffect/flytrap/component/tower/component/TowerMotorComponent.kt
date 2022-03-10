package com.teambutterflyeffect.flytrap.component.tower.component

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode
import com.ctre.phoenix.motorcontrol.can.VictorSPX
import com.teambutterflyeffect.flytrap.component.tower.message.TowerMotorMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage

class TowerMotorComponent(context: LifecycleContext) : LifecycleObject(context) {
    val motor = VictorSPX(8)
    var message: TowerMotorMessage? = null

    override fun onTick(context: ObjectContext<*>) {
        if(message?.isValid() == false) message = null
        motor.set(VictorSPXControlMode.PercentOutput, ((message?.content?.value ?: 0.0) * 1.0))
    }

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(
        TowerMotorMessage::class.java
    )

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
        if(message is TowerMotorMessage && message.isValid()) {
            this@TowerMotorComponent.message = message
        }
    }
}