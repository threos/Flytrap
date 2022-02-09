package com.teambutterflyeffect.flytrap.system.lifecycle.data

import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

open class ObjectMessage (
    val intent: Intent,
)

open class DataMessage<V: Any> (
    intent: Intent,
    val content: V
) : ObjectMessage(intent)

open class StringMessage (
    intent: Intent,
    content: String,
) : DataMessage<String>(intent, content)

open class IntMessage (
    intent: Intent,
    content: Int,
) : DataMessage<Int>(intent, content)