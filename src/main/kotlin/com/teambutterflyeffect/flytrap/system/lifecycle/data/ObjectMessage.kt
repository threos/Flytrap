package com.teambutterflyeffect.flytrap.system.lifecycle.data

import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

open class ObjectMessage (
    val intent: Intent,
    timeoutInMillis: Long? = null,
) {
    val validUntil = timeoutInMillis?.let { it + System.currentTimeMillis() }

    fun runWhen(filter: MessageFilter, func: () -> Unit) {
        if(filter(this)) func()
    }

    fun isValid(): Boolean = validUntil?.let {
        validUntil > System.currentTimeMillis()
    } ?: true
}

open class DataMessage<V: Any?> (
    intent: Intent,
    val content: V,
    timeoutInMillis: Long? = null,
) : ObjectMessage(intent, timeoutInMillis)

open class StringMessage (
    intent: Intent,
    content: String,
    timeoutInMillis: Long? = null,
) : DataMessage<String>(intent, content, timeoutInMillis)

open class IntMessage (
    intent: Intent,
    content: Int,
    timeoutInMillis: Long? = null,
) : DataMessage<Int>(intent, content, timeoutInMillis)