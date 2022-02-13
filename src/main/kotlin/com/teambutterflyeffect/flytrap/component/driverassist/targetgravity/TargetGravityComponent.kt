package com.teambutterflyeffect.flytrap.component.driverassist.targetgravity

import com.teambutterflyeffect.flytrap.component.driverassist.Alliance
import com.teambutterflyeffect.flytrap.component.driverassist.AssistConfig
import com.teambutterflyeffect.flytrap.component.driverassist.targetgravity.message.GravityForceMessage
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.MapDataMessage
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.VisionMapEntity
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents
class TargetGravityComponent(context: LifecycleContext) : LifecycleObject(context) {
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

            this@TargetGravityComponent.context.post(
                GravityForceMessage(
                    Intents.create(context, null),
                    gravityForce,
                    timeoutInMillis = 200
                )
            )
        }
    }

    fun gravityObjectFromMapEntity(entity: VisionMapEntity): GravityObject? {
        return when (entity.id) {
            "blue_ball" -> ballGravity(Alliance.BLUE, entity)
            "red_ball" -> ballGravity(Alliance.RED, entity)
            else -> null
        }
    }

    fun ballGravity(ballOf: Alliance, entity: VisionMapEntity): GravityObject {
        val mass = AssistConfig.BALL_MASS * (if (ballOf == AssistConfig.CURRENT_ALLIANCE) {
            1.0f
        } else -1.0f)

        return GravityObject((entity.x_0 + entity.x_1) / 2, entity.distanceToReference, mass)
    }
}




