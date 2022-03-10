package com.teambutterflyeffect.flytrap.component.driverassist.targetgravity

import com.teambutterflyeffect.flytrap.component.debugserver.ROBOT_CONFIGURATION
import com.teambutterflyeffect.flytrap.component.driverassist.AssistConfig
import com.teambutterflyeffect.flytrap.component.driverassist.targetgravity.message.GravityForceMessage
import com.teambutterflyeffect.flytrap.component.flylogger.LogLevel
import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.MapDataMessage
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.VisionMapEntity
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents
import edu.wpi.first.wpilibj.DriverStation

class TargetGravityComponent(context: LifecycleContext) : LifecycleObject(context) {
    val TAG = "TargetGravityComponent"
    override fun onTick(context: ObjectContext<*>) {}

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(MapDataMessage::class.java)

    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
        if (message is MapDataMessage && message.isValid()) {
            val gravityForce = GravityComposer.compose(
                message.entities.mapNotNull {
                    gravityObjectFromMapEntity(it)
                }
            )
            log(TAG, "Force: ${gravityForce.force}", LogLevel.VERBOSE)


            this@TargetGravityComponent.context.post(
                GravityForceMessage(
                    Intents.create(context, null),
                    gravityForce,
                    timeoutInMillis = 200
                )
            )
        } else {
            log(TAG, "Invalid message!")
        }
    }

    fun gravityObjectFromMapEntity(entity: VisionMapEntity): GravityObject? {
        return when (entity.id) {
            "blue_ball" -> ballGravity(DriverStation.Alliance.Blue, entity)
            "red_ball" -> ballGravity(DriverStation.Alliance.Red, entity)
            else -> null
        }
    }

    fun ballGravity(ballOf: DriverStation.Alliance, entity: VisionMapEntity): GravityObject {
        val mass = AssistConfig.BALL_MASS * (if (ballOf == ROBOT_CONFIGURATION.alliance) {
            1.0f
        } else -1.0f)

        return GravityObject((entity.x_0 + entity.x_1), entity.distanceToReference, mass)
    }
}




