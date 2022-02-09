package com.teambutterflyeffect.flytrap.system.lifecycle.objects

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject

open class ExternalReference<T: Any?> (
    val objectClass: Class<T>,
)