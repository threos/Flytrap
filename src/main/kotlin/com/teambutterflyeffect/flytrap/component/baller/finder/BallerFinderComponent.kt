package com.teambutterflyeffect.flytrap.component.baller.finder

import com.teambutterflyeffect.flytrap.component.baller.light.BallerLightComponent
import com.teambutterflyeffect.flytrap.component.baller.light.BallerLightMessage
import com.teambutterflyeffect.flytrap.component.baller.light.animation.ConstantAnimation
import com.teambutterflyeffect.flytrap.component.debugserver.ROBOT_CONFIGURATION
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveComponent
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveData
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveMessage
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.HubDataMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents

class BallerFinderComponent(context: LifecycleContext) : LifecycleObject(context) {
    var done = false
    override fun onTick(context: ObjectContext<*>) {}

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(
        HubDataMessage::class.java
    )

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
        if(!done && message is HubDataMessage && message.isValid()) {
            if(message.content.tArea > ROBOT_CONFIGURATION.BALLER_IDEAL_TARGET_AREA) {
                done = true
                this@BallerFinderComponent.context.post(
                    BallerFinderResultMessage(
                        Intents.create(context, RobotDriveComponent::class.java),
                    )
                )
                return
            }
            this@BallerFinderComponent.context.post(
                RobotDriveMessage(
                    Intents.create(context, RobotDriveComponent::class.java),
                    RobotDriveData(0.7, 0.0),
                    timeoutInMillis = 100
                )
            )

            this@BallerFinderComponent.context.post(
                BallerLightMessage(
                    Intents.create(context, BallerLightComponent::class.java),
                    ConstantAnimation(0, 255, 0)
                )
            )
        }
    }
}