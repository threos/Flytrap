package com.teambutterflyeffect.flytrap.system.lifecycle.objects

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject

data class ObjectReference<T: LifecycleObject> (
    val objectClass: Class<T>,
)