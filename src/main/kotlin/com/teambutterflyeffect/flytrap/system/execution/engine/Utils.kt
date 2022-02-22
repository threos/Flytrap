package com.teambutterflyeffect.flytrap.system.execution.engine

import kotlin.math.max
import kotlin.math.min

object Utils {
    fun getIdealThreadCount(): Int {
        return try {
            min(max(Runtime.getRuntime().availableProcessors() * 2, 4), 16)
        } catch (e: Throwable) {
            e.printStackTrace()
            4
        }
    }
}