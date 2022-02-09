package com.teambutterflyeffect.flytrap.system.state.cubit

import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ExternalReference
import java.util.*

abstract class  MultiStateCubit<V: Any?> {
    private val state = IdentityHashMap<ExternalReference<V>, V>()

    @Synchronized
    fun emit(reference: ExternalReference<V>, value: V): Boolean {
        return state.let {
            val state = it[reference]
            if(state != value) {
                it[reference] = value
                update(reference, value)
                return@let true
            }
            return@let false
        }
    }

    abstract fun update(reference: ExternalReference<V>, value: V)
}