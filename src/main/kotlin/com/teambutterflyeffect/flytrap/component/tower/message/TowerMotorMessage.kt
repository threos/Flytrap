package com.teambutterflyeffect.flytrap.component.tower.message

import com.teambutterflyeffect.flytrap.component.intake.IntakeDirection
import com.teambutterflyeffect.flytrap.system.lifecycle.data.DataMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

class TowerMotorMessage(intent: Intent, content: IntakeDirection, timeoutInMillis: Long? = 2000) :
    DataMessage<IntakeDirection>(intent, content, timeoutInMillis)