package com.teambutterflyeffect.flytrap.component.baller.aligner

import com.teambutterflyeffect.flytrap.component.baller.light.BallerLightComponent
import com.teambutterflyeffect.flytrap.component.baller.light.BallerLightMessage
import com.teambutterflyeffect.flytrap.component.baller.light.animation.ConstantAnimation
import com.teambutterflyeffect.flytrap.component.debugserver.ROBOT_CONFIGURATION
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveComponent
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveData
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveMessage
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.HubDataMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents
import kotlin.math.abs
import kotlin.math.sign

class BallerAlignerComponent(context: LifecycleContext) : LifecycleObject(context) {
    val tag = "BallerAlignerComponent"
    var activeUntil: Long = 0
    override fun onTick(context: ObjectContext<*>) {}

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(
        AlignerMessage::class.java,
        HubDataMessage::class.java
    )

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)

        //log(tag, "m: $message - ${message.isValid()}")
        //log(tag, "active: ${activeUntil >= System.currentTimeMillis()}")

        if (message is HubDataMessage && message.isValid() && activeUntil >= System.currentTimeMillis()) {
            //log(tag, "Run!")
            this@BallerAlignerComponent.context.post(
                RobotDriveMessage(
                    Intents.create(context, RobotDriveComponent::class.java),
                    RobotDriveData(
                        (((1.0 - message.content.y) - ROBOT_CONFIGURATION.BALLER_IDEAL_Y_POINT).takeIf { abs(it) > 0.1 } ?: 0.0).sign * 0.68,
                        ((message.content.x - ROBOT_CONFIGURATION.BALLER_IDEAL_X_POINT).takeIf { abs(it) > 0.08 } ?: 0.0).sign * 0.62
                    ),
                    timeoutInMillis = 200,
                    allowModifier = false,
                )
            )

            this@BallerAlignerComponent.context.post(
                BallerLightMessage(
                    Intents.create(context, BallerLightComponent::class.java),
                    ConstantAnimation(0, 255, 0)
                )
            )
        } else if(message is AlignerMessage && message.isValid()) {
            log(tag, "aligner")
            activeUntil = message.validUntil!!

            this@BallerAlignerComponent.context.post(
                BallerLightMessage(
                    Intents.create(context, BallerLightComponent::class.java),
                    ConstantAnimation(0, 255, 0)
                )
            )
        }
    }
}