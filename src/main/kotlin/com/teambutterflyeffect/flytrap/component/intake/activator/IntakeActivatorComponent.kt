package com.teambutterflyeffect.flytrap.component.intake.activator

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode
import com.ctre.phoenix.motorcontrol.can.VictorSPX
import com.teambutterflyeffect.flytrap.component.intake.IntakeMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import kotlin.math.max
import kotlin.math.min

class IntakeActivatorComponent(context: LifecycleContext) : LifecycleObject(context) {
    var message: IntakeActivatorMessage? = null
    val activator = VictorSPX(4)
    var active = false

    override fun onTick(context: ObjectContext<*>) {
        if (!active && message?.isValid() == false) message = null
        activator.set(VictorSPXControlMode.PercentOutput, message?.let {
            0.0
        } ?: 0.0)
    }

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(
        IntakeActivatorMessage::class.java,
    )

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
        if (message is IntakeActivatorMessage && message.isValid() && !active) {
            this@IntakeActivatorComponent.message = message
            active = true
        }
    }
}