package com.teambutterflyeffect.flytrap.system.execution.engine

object Utils {
    fun getIdealThreadCount(): Int {
        return try {
            Runtime.getRuntime().availableProcessors()
        } catch (e: Throwable) {
            e.printStackTrace()
            4
        }
    }
}