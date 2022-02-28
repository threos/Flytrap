package com.teambutterflyeffect.flytrap.component.tower.positioning

import com.teambutterflyeffect.flytrap.component.flylogger.log
import com.teambutterflyeffect.flytrap.component.tower.message.TowerContentMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleContext
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intents
import com.teambutterflyeffect.flytrap.system.state.cubit.Cubit

class TowerContentCubit : Cubit<List<TowerTarget>>(emptyList()) {
    val tag = "TowerContentCubit"
    override fun update(context: ObjectContext<*>, value: List<TowerTarget>) {
        log(
            tag, "Tower content update: ${
                value.joinToString {
                    it.position.name
                }
            }"
        )
        LifecycleContext.post(
            TowerContentMessage(
                Intents.create(context, null),
                value,
            )
        )
    }

}