package com.teambutterflyeffect.flytrap.component.drivecontrol.hanger

import com.teambutterflyeffect.flytrap.system.lifecycle.data.DataMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

class HangerMessage(intent: Intent, content: Double, timeoutInMillis: Long? = 100) :
    DataMessage<Double>(intent, content, timeoutInMillis)