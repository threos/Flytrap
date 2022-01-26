package com.teambutterflyeffect.flytrap.control.input

open class InputChannel(val id: Int, initialData: Double? = null) {
    var data: Double = 0.0

    init {
        initialData?.let {
            data = it
        }
    }
}