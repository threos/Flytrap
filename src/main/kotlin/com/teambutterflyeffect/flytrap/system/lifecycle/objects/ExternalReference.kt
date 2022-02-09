package com.teambutterflyeffect.flytrap.system.lifecycle.objects

import com.teambutterflyeffect.flytrap.system.lifecycle.LifecycleObject

open class ExternalReference<T: Any?> (
    val objectClass: Class<T>,
)

open class ExternalValueReference<T: Any?>(val obj: T, objectClass: Class<T>) : ExternalReference<T>(objectClass) {

}