package com.teambutterflyeffect.flytrap.robot.component

import com.teambutterflyeffect.flytrap.component.baller.aligner.AlignerMessage
import com.teambutterflyeffect.flytrap.component.baller.aligner.BallerAlignerComponent
import com.teambutterflyeffect.flytrap.component.baller.light.BallerLightComponent
import com.teambutterflyeffect.flytrap.component.baller.light.BallerLightMessage
import com.teambutterflyeffect.flytrap.component.baller.light.OperationLightComponent
import com.teambutterflyeffect.flytrap.component.baller.light.animation.ConstantAnimation
import com.teambutterflyeffect.flytrap.component.baller.shooter.ShooterComponent
import com.teambutterflyeffect.flytrap.component.baller.shooter.ShooterMessage
import com.teambutterflyeffect.flytrap.component.debugserver.ROBOT_CONFIGURATION
import com.teambutterflyeffect.flytrap.component.drivecontrol.hanger.HangerComponent
import com.teambutterflyeffect.flytrap.component.drivecontrol.hanger.HangerMessage
import com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive.*
import com.teambutterflyeffect.flytrap.component.driverassist.targetgravity.GravityObject
import com.teambutterflyeffect.flytrap.component.driverassist.targetgravity.message.GravityForceMessage
import com.teambutterflyeffect.flytrap.component.flylogger.LogLevel
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.MapDataMessage
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.VisionMapEntity
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
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ObjectReference
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.XboxController
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class TeleopComponent(context: LifecycleContext) : LifecycleObject(context) {
    val controller = XboxController(0)
    var gravityMessage: GravityForceMessage? = null
    var i = 0
    var lastXPressed: Long = 0
    var lastYPressed: Long = 0
    var lastBPressed: Long = 0
    var lastAPressed: Long = 0

    val TAG = "TeleopComponent"

    override fun onTick(context: ObjectContext<*>) {
        //log(TAG, "tick")
        val x = controller.getRawAxis(XboxController.Axis.kLeftX.value)
        val y = -controller.getRawAxis(XboxController.Axis.kLeftY.value)
        val steer = x * 1.0 + if (x < 0) -(abs(y) * 0.2) else abs(y) * 0.2
        (gravityMessage?.content).let {
            val boost = (gravityMessage?.content?.force?.toDouble() ?: 0.0) * (1.0 - abs(
                gravityMessage?.content?.x ?: 1.0f
            ))
            controller.setRumble(
                GenericHID.RumbleType.kLeftRumble,
                abs(min(it?.x?.toDouble() ?: 0.0, 0.0)) * 0.6 + boost
            )
            controller.setRumble(
                GenericHID.RumbleType.kRightRumble,
                abs(max(it?.x?.toDouble() ?: 0.0, 0.0)) * 0.6 + boost
            )

        }

        this@TeleopComponent.context.post(
            RobotDriveMessage(
                Intents.create(
                    context,
                    RobotDriveComponent::class.java
                ),
                RobotDriveData(y, steer * 0.95 /*+ ((gravityMessage?.let {
                    if (it.isValid()) {
                        unit(it.content.x) * (y * 0.8) * it.content.force
                    } else {
                        0.0
                    }
                } ?: 0.0) * -(1.0 - abs(x)) * 0.8)*/
                ),
                allowModifier = true,
            )
        )

        this@TeleopComponent.context.post(
            HangerMessage(
                Intents.create(context, HangerComponent::class.java),
                controller.rightY.let {
                    if(abs(it) < 0.3) {
                        0.0
                    } else it
                }
            )
        )

        if(controller.xButton && lastXPressed < System.currentTimeMillis() - 200) {
            log(TAG, "Reverse!")
            this@TeleopComponent.context.post(
                DriveModifierMessage(
                    Intents.create(context, RobotDriveComponent::class.java),
                    ReverseModifier(),
                    timeoutInMillis = 100000,
                )
            )
            lastXPressed = System.currentTimeMillis()
        }

        if(controller.pov != -1) {
            this@TeleopComponent.context.post(
                DriveModifierMessage(
                    Intents.create(context, RobotDriveComponent::class.java),
                    DefaultModifier(),
                    timeoutInMillis = 200,
                )
            )
        }

        if(controller.bButton && lastBPressed < System.currentTimeMillis() - 200) {
            this@TeleopComponent.context.post(
                DriveModifierMessage(
                    Intents.create(context, RobotDriveComponent::class.java),
                    PrecisionModifier(),
                    timeoutInMillis = 200,
                )
            )
            lastBPressed = System.currentTimeMillis()
        }

        if(controller.aButton && lastAPressed < System.currentTimeMillis() - 200) {
            this@TeleopComponent.context.post(
                DriveModifierMessage(
                    Intents.create(context, RobotDriveComponent::class.java),
                    TurboModifier(),
                    timeoutInMillis = 10000,
                )
            )
            lastAPressed = System.currentTimeMillis()
        }


        if(controller.rightBumper) {
            this@TeleopComponent.context.post(
                IntakeMessage(
                    Intents.create(
                        context,
                        IntakeComponent::class.java
                    ),
                    IntakeDirection.IN,
                    timeoutInMillis = 200
                )
            )

            this@TeleopComponent.context.post(
                TowerMotorMessage(
                    Intents.create(
                        context,
                        TowerMotorComponent::class.java
                    ),
                    IntakeDirection.IN,
                    timeoutInMillis = 200
                )
            )
        }

        if (controller.rightTriggerAxis > 0.5) {
            this@TeleopComponent.context.post(
                ShooterMessage(
                    Intents.create(
                        context,
                        ShooterComponent::class.java
                    ),
                    ROBOT_CONFIGURATION.BALLER_RPM
                )
            )
        }

        if (controller.leftBumper) {
            this@TeleopComponent.context.post(
                IntakeMessage(
                    Intents.create(
                        context,
                        IntakeComponent::class.java
                    ),
                    IntakeDirection.OUT,
                    timeoutInMillis = 200
                )
            )

            this@TeleopComponent.context.post(
                TowerMotorMessage(
                    Intents.create(
                        context,
                        TowerMotorComponent::class.java
                    ),
                    IntakeDirection.OUT,
                    timeoutInMillis = 200
                )
            )
        }

        if(controller.leftTriggerAxis > 0.25) {
            this@TeleopComponent.context.post(
                AlignerMessage(
                    Intents.create(context, BallerAlignerComponent::class.java),
                )
            )
        }

        if(controller.yButton) {
            this@TeleopComponent.context.post(
                ShooterMessage(
                    Intents.create(
                        context,
                        ShooterComponent::class.java
                    ),
                    ROBOT_CONFIGURATION.BALLER_LOW_RPM
                )
            )
            //lastYPressed = System.currentTimeMillis()
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

    override fun onDestroy(context: ObjectContext<*>) {
        super.onDestroy(context)
        controller.setRumble(
            GenericHID.RumbleType.kLeftRumble,
            0.0
        )
        controller.setRumble(
            GenericHID.RumbleType.kRightRumble,
            0.0
        )
    }

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
        if (message is GravityForceMessage && message.isValid()) {
            log(TAG, "Gravity message")
            gravityMessage = message
        } else if(message is MapDataMessage && message.isValid()) {
            log(TAG, "MapData message")
            for(ball in message.entities) {
                if(ballGravity(ball) == true) {
                    this@TeleopComponent.context.post(
                        IntakeMessage(
                            Intents.create(
                                context,
                                IntakeComponent::class.java
                            ),
                            IntakeDirection.IN,
                            timeoutInMillis = 200
                        )
                    )
                    break
                }
            }
        } else {
            log(TAG, "Invalid gravity message")
        }
    }

    fun ballGravity(entity: VisionMapEntity): Boolean? {
        return when (entity.id) {
            "blue_ball" -> ROBOT_CONFIGURATION.alliance == DriverStation.Alliance.Blue
            "red_ball" ->ROBOT_CONFIGURATION.alliance == DriverStation.Alliance.Red
            else -> null
        }
    }

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(
        GravityForceMessage::class.java,
        MapDataMessage::class.java
    )

    override fun components(): Array<ObjectReference<out LifecycleObject>> = arrayOf(
        ObjectReference(OperationLightComponent::class.java),
        ObjectReference(HangerComponent::class.java),
        ObjectReference(BallerAlignerComponent::class.java),
    )
}