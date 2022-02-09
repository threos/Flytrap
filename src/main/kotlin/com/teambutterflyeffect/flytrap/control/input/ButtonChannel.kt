package com.teambutterflyeffect.flytrap.control.input

abstract class ButtonChannel(id: Int, initialData: Boolean) : InputChannel(id, initialData = if(initialData) {1.0} else 0.0) {
    fun isPressed(): Boolean {
        return data == 1.0
    }

    fun isNotPressed(): Boolean {
        return !isPressed()
    }
}