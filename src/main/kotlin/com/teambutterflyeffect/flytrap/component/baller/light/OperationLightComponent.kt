package com.teambutterflyeffect.flytrap.component.baller.light

import com.teambutterflyeffect.flytrap.component.baller.light.animation.FadeAnimation
import com.teambutterflyeffect.flytrap.component.baller.light.animation.WarningAnimation
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents

class OperationLightComponent(context: LifecycleContext) : LifecycleObject(context) {
    private val operationLightAnimator = FadeAnimation(0, 120, 200, 60)
    override fun onTick(context: ObjectContext<*>) {}

    override fun onCreate(context: ObjectContext<*>) {
        super.onCreate(context)
        this@OperationLightComponent.context.post(
            BallerLightMessage(
                Intents.create(context, BallerLightComponent::class.java),
                operationLightAnimator,
                timeoutInMillis = null
            )
        )
    }

    override fun onDestroy(context: ObjectContext<*>) {
        super.onDestroy(context)
        this@OperationLightComponent.context.post(
            BallerLightMessage(
                Intents.create(context, BallerLightComponent::class.java),
                WarningAnimation(20, 200, 80),
                timeoutInMillis = 1500
            )
        )
    }
}