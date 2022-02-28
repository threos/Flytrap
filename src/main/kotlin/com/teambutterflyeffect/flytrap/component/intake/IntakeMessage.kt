package com.teambutterflyeffect.flytrap.component.intake

import com.teambutterflyeffect.flytrap.system.lifecycle.data.DataMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

class IntakeMessage(intent: Intent, content: IntakeDirection, timeoutInMillis: Long? = 4000) :
    DataMessage<IntakeDirection>(intent, content, timeoutInMillis)

enum class IntakeDirection(val value: Double) {
    IN(1.0),
    OUT(-1.0),
}