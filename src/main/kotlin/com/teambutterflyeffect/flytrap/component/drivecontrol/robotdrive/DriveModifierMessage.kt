package com.teambutterflyeffect.flytrap.component.drivecontrol.robotdrive

import com.teambutterflyeffect.flytrap.system.lifecycle.data.DataMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

class DriveModifierMessage(intent: Intent, content: ModifierBuilder, timeoutInMillis: Long? = 10000) :
    DataMessage<ModifierBuilder>(intent, content, timeoutInMillis) {
}

typealias ModifierBuilder = (previous: Double, default: Double) -> Double