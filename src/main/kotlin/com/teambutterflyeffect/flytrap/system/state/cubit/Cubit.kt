package com.teambutterflyeffect.flytrap.system.state.cubit

import com.teambutterflyeffect.flytrap.system.lifecycle.ObjectContext
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ExternalReference
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.ExternalValueReference
import java.util.*

abstract class  MultiStateCubit<K: Any?, V: Any?> {
    private val state = IdentityHashMap<ExternalReference<out K>, V>()

    @Synchronized
    fun emit(context: ObjectContext<*>, reference: ExternalValueReference<out K>, value: V): Boolean {
        return state.let {
            val state = it[reference]
            if(state != value) {
                it[reference] = value
                update(context, reference, value)
                return@let true
            }
            return@let false
        }
    }

    abstract fun update(context: ObjectContext<*>, reference: ExternalValueReference<out K>, value: V)
}