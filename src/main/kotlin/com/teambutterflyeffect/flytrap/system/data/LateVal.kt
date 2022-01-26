package com.teambutterflyeffect.flytrap.system.data

class VolatileLateVal<D : Any> (val defaultValue: D? = null){
    @Volatile var data: D? = null
        @Throws(IllegalStateException::class) get() {
            return field ?: (defaultValue ?: throw IllegalStateException("LateVal isn't initialized yet."))
        }
        @Throws(IllegalStateException::class) set(value) {
            if (field != null) throw IllegalAccessError("LateVal is already initialized. LateVals can't be changed after initialization.")
            field = value
        }

    fun isInitialized(): Boolean = data != null
}