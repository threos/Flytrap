package com.teambutterflyeffect.flytrap.system.lifecycle.objects

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject
import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext

data class Intent internal constructor(val sender: Class<out LifecycleObject>,
                                       val target: Class<out LifecycleObject>?) {
    override fun toString(): String {
        return "<Intent sender=$sender target=$target>"
    }
}

object Intents {
    fun create(context: ObjectContext<out LifecycleObject>, target: Class<out LifecycleObject>?): Intent {
        return Intent(context.objectClass, target)
    }
}