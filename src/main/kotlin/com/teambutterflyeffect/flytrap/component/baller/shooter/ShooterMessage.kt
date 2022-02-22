package com.teambutterflyeffect.flytrap.component.baller.shooter

import com.teambutterflyeffect.flytrap.system.lifecycle.data.DataMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

// RPM is not an accurate representation for actual motor output but we will use it to get a logical integer value to work on instead of 0.0-1.0 range.
class ShooterMessage(intent: Intent, desiredRpm: Int, timeoutInMillis: Long? = 200) :
    DataMessage<Int>(intent, desiredRpm, timeoutInMillis)