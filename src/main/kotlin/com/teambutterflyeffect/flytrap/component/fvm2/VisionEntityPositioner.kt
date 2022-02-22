package com.teambutterflyeffect.flytrap.component.fvm2

import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.MapDataMessage
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.VisionDataMessage
import com.teambutterflyeffect.flytrap.component.fvm2.protocol.VisionMapEntity
import com.teambutterflyeffect.flytrap.component.fvm2.transformation.MidpointTransformation
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents

const val TAG = "VisionEntityPositioner"
class VisionEntityPositioner(context: LifecycleContext) : LifecycleObject(context) {
    private val transformation = MidpointTransformation(VERTICAL_FOV_IN_DEGREES, CAMERA_NEGATIVE_MOUNTING_ANGLE_IN_DEGREES)

    override fun onTick(context: ObjectContext<*>) {}

    override fun subscriptions(): Array<Class<out ObjectMessage>> = arrayOf(VisionDataMessage::class.java)


    override fun onMessage(context: ObjectContext<*>, message: ObjectMessage) {
        super.onMessage(context, message)
        if (message is VisionDataMessage) {
            this.context.post(
                MapDataMessage(
                    message.entities.map {
                        VisionMapEntity(it, transformation.transformHorizontalDistance((it.y_0 + it.y_1)) / 2)
                    },
                    Intents.create(context, null),
                ),
            )
        }
    }
}