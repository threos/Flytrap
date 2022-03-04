package com.teambutterflyeffect.flytrap.component.autopilot

import com.teambutterflyeffect.flytrap.component.debugserver.ROBOT_CONFIGURATION
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveComponent
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveData
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveMessage
import com.teambutterflyeffect.flytrap.component.driverassist.targetgravity.message.GravityForceMessage
import com.teambutterflyeffect.flytrap.component.intake.IntakeComponent
import com.teambutterflyeffect.flytrap.component.intake.IntakeDirection
import com.teambutterflyeffect.flytrap.component.intake.IntakeMessage
import com.teambutterflyeffect.flytrap.component.tower.component.TowerMotorComponent
import com.teambutterflyeffect.flytrap.component.tower.message.TowerMotorMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

class AutopilotComponent(context: LifecycleContext) : LifecycleObject(context) {
    var activeUntil: Long? = null
    var gravityMessage: GravityForceMessage? = null

    override fun onTick(context: ObjectContext<*>) {
        activeUntil?.takeIf { it > System.currentTimeMillis() }.let {
            if (gravityMessage?.isValid() == true) {
                this@AutopilotComponent.context.post(
                    RobotDriveMessage(
                        Intents.create(context, RobotDriveComponent::class.java),
                        (gravityMessage!!.content.force).sign.let {
                            RobotDriveData(
                                max(it * 0.55 * (1.0 - (abs(gravityMessage!!.content.x) / 3.5)), 0.0),
                                (gravityMessage!!.content.x).sign * -0.15 + min(
                                    max(gravityMessage!!.content.x, -1f),
                                    1f
                                ) * -0.3 * it,
                            )
                        },
                        timeoutInMillis = 100
                    )
                )
                gravityMessage?.content?.force.takeIf { it != 0f }?.let {
                    (if (it - 1f >= 0f) {
                        IntakeDirection.IN
                    } else IntakeDirection.OUT).let {
                        this@AutopilotComponent.context.post(
                            IntakeMessage(
                                Intents.create(context, IntakeComponent::class.java),
                                it
                            )
                        )
                        if (it == IntakeDirection.IN) {
                            this@AutopilotComponent.context.post(
                                TowerMotorMessage(
                                    Intents.create(context, TowerMotorComponent::class.java),
                                    it
                                )
                            )
                        }
                    }
                }
            } else {
                this@AutopilotComponent.context.post(
                    RobotDriveMessage(
                        Intents.create(context, RobotDriveComponent::class.java),
                        RobotDriveData(
                            0.0,
                            ROBOT_CONFIGURATION.driverAssistInitialDirection.rotation * 0.5,
                        )
                    )
                )
            }
        }
    }

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
        if (message is AutopilotMessage) {
            activeUntil = message.validUntil
        } else if (message is GravityForceMessage && message.isValid()) {
            gravityMessage = message
        }
    }

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(
        AutopilotMessage::class.java,
        GravityForceMessage::class.java,
    )
}