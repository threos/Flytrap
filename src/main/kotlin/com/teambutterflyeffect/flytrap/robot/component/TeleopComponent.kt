package com.teambutterflyeffect.flytrap.robot.component

import com.teambutterflyeffect.flytrap.component.baller.shooter.ShooterComponent
import com.teambutterflyeffect.flytrap.component.baller.shooter.ShooterMessage
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveComponent
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveData
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.RobotDriveMessage
import com.teambutterflyeffect.flytrap.component.driverassist.targetgravity.message.GravityForceMessage
import com.teambutterflyeffect.flytrap.component.flylogger.LogLevel
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference
import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.XboxController
import java.lang.Math.abs
import kotlin.math.max
import kotlin.math.min

class TeleopComponent(context: LifecycleContext) : LifecycleObject(context) {
    val controller = XboxController(0)
    var gravityMessage: GravityForceMessage? = null
    var i = 0

    val TAG = "TeleopComponent"

    override fun onTick(context: ObjectContext<*>) {
        //log(TAG, "tick")
        val x = controller.getRawAxis(XboxController.Axis.kLeftX.value)
        val y = -controller.getRawAxis(XboxController.Axis.kLeftY.value)
        val steer = x * 0.9 + if (x < 0) -(kotlin.math.abs(y) * 0.1) else kotlin.math.abs(y) * 0.1
        (gravityMessage?.content?.force?.toDouble() ?: 0.0).let {
            controller.setRumble(
                GenericHID.RumbleType.kLeftRumble,
                kotlin.math.abs(min(it, 0.0)) * 0.8
            )
            controller.setRumble(
                GenericHID.RumbleType.kRightRumble,
                kotlin.math.abs(max(it, 0.0)) * 0.8
            )

        }

        this@TeleopComponent.context.post(
            RobotDriveMessage(
                Intents.create(
                    context,
                    RobotDriveComponent::class.java
                ),
                RobotDriveData(y * 0.72, x * 0.7 + (gravityMessage?.let {
                    if (it.isValid()) {
                        unit(it.content.x * it.content.force) * 0.2
                    } else {
                        0.0
                    }
                } ?: 0.0)
                ),
            )
        )
        if (controller.rightTriggerAxis > 0.5) {
            this@TeleopComponent.context.post(
                ShooterMessage(
                    Intents.create(
                        context,
                        ShooterComponent::class.java
                    ),
                    5200
                )
            )
        }
    }

    fun unit(value: Float): Float {
        return if (value > 0) {
            1.0f
        } else if (value < 0) {
            -1.0f
        } else 0.0f
    }

    override fun onCreate(context: ObjectContext<*>) {
        super.onCreate(context)
        log(TAG, "onCreate", LogLevel.VERBOSE)
        //test(context)
    }

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
        if (message is GravityForceMessage && message.isValid()) {
            log(TAG, "Gravity message")
            gravityMessage = message
        } else {
            log(TAG, "Invalid gravity message")
        }
    }

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(GravityForceMessage::class.java)

    override fun components(): Array<ObjectReference<out LifecycleObject>> = arrayOf()
}