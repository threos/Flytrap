package com.teambutterflyeffect.flytrap.system.lifecycle.objects

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject

open class ObjectReference<T: LifecycleObject> (
    val objectClass: Class<T>,
)