package com.teambutterflyeffect.flytrap.system.lifecycle.data

interface MessageFilter {
    fun accept(message: ObjectMessage): Boolean
}