package com.teambutterflyeffect.flytrap.component.intake

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode
import com.ctre.phoenix.motorcontrol.can.VictorSPX
import com.teambutterflyeffect.flytrap.component.intake.activator.IntakeActivatorComponent
import com.teambutterflyeffect.flytrap.component.intake.activator.IntakeActivatorMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference
import kotlin.math.max
import kotlin.math.min

class IntakeComponent(context: LifecycleContext) : LifecycleObject(context) {
    var message: IntakeMessage? = null
    val motor = VictorSPX(7)

    override fun onTick(context: ObjectContext<*>) {
        if(message?.isValid() == false) message = null

        motor.set(VictorSPXControlMode.PercentOutput, min(max(message?.content?.value ?: 0.0, -1.0), 0.65) * -1.0)
    }

    override fun components(): Array<ObjectReference<out LifecycleObject>> = arrayOf(
        ObjectReference(IntakeActivatorComponent::class.java)
    )

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(
            IntakeMessage::class.java,
    )

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
        if(message is IntakeMessage && message.isValid()) {
            this@IntakeComponent.message = message
            this@IntakeComponent.context.post(
                IntakeActivatorMessage(
                    Intents.create(context, IntakeActivatorComponent::class.java)
                )
            )
        }
    }
}