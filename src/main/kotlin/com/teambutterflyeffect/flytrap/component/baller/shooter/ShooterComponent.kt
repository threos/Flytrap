package com.teambutterflyeffect.flytrap.component.baller.shooter

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import com.teambutterflyeffect.flytrap.component.baller.light.BallerLightComponent
import com.teambutterflyeffect.flytrap.component.baller.light.BallerLightMessage
import com.teambutterflyeffect.flytrap.component.baller.light.animation.WarningAnimation
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents
import kotlin.math.abs
import kotlin.math.min

class ShooterComponent(context: LifecycleContext) : LifecycleObject(context) {
    val tag = "ShooterComponent"
    private var shooterMessage: ShooterMessage? = null
     set(value) {
         field = value
         value?.content?.let {
             if(speed == 0.0) {
                 warningEnd = System.currentTimeMillis() + 1800
                 warmupStage1End = warningEnd + warmupDuration
                 warmupEnd = warmupStage1End + 2000
             }
             speed = it.div(11000.0)
         }
     }
    var speed = 0.0
    var warmupEnd: Long = 0
    var warmupStage1End: Long = 0
    var warningEnd: Long = 0
    var warmupDuration = 2000
    private val motorLeft = CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val motorRight = CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless)

    val warningAnimation = WarningAnimation(250, 30, 0)

    override fun onCreate(context: ObjectContext<*>) {
        super.onCreate(context)
        motorLeft.inverted = true
    }

    fun spin(speed: Double) {
        val value = if(warningEnd > System.currentTimeMillis()) { 0.0 } else min(speed, 1.0)
        motorLeft.set(value)
        motorRight.set(value)
    }

    override fun onTick(context: ObjectContext<*>) {
        if(shooterMessage?.isValid() == true) {
            spin(
                System.currentTimeMillis().let {
                    if(warningEnd < it) {
                        if(warmupEnd < it) {
                            speed
                        } else if(warmupStage1End < it) {
                            speed * 1.12
                        } else speed * abs(1.0 - ((warmupStage1End - it).toDouble() / warmupDuration.toDouble()))
                    } else 0.0
                }
            )
        } else {
            speed = 0.0
            spin(0.0)
        }
    }

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(
        ShooterMessage::class.java,
    )

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
        if(message is ShooterMessage && message.isValid()) {
            shooterMessage = message
            if(warningEnd > System.currentTimeMillis()) {
                this@ShooterComponent.context.post(
                    BallerLightMessage(
                        Intents.create(context, BallerLightComponent::class.java),
                        warningAnimation,
                        timeoutInMillis = warningEnd - System.currentTimeMillis() + 1000
                    )
                )
            }
        }
    }

    override fun onDestroy(context: ObjectContext<*>) {
        super.onDestroy(context)
        motorLeft.set(0.0)
        motorRight.set(0.0)
        motorLeft.close()
        motorRight.close()
    }
}