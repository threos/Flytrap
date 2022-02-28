package com.teambutterflyeffect.flytrap.component.baller.aligner

import com.teambutterflyeffect.flytrap.component.baller.BallerConfig
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

        if (message is HubDataMessage && message.isValid() && activeUntil > System.currentTimeMillis()) {
            this@BallerAlignerComponent.context.post(
                RobotDriveMessage(
                    Intents.create(context, RobotDriveComponent::class.java),
                    RobotDriveData(
                        -(((message.content.y - BallerConfig.BALLER_IDEAL_Y_POINT)).takeIf {
                            abs(it) > 0.03
                        } ?: 0.0).sign * 0.4,
                        ((message.content.x - BallerConfig.BALLER_IDEAL_X_POINT).takeIf {
                            abs(it) > 0.03
                        } ?: 0.0).sign * 0.4
                    ),
                    timeoutInMillis = 100
                )
            )
        } else if(message is AlignerMessage && message.isValid()) {
            log(tag, "Message!")
            activeUntil = message.validUntil!!
        }
    }
}