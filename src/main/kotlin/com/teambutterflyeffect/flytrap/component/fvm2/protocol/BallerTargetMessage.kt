package com.teambutterflyeffect.flytrap.component.fvm2.protocol

import com.teambutterflyeffect.flytrap.system.lifecycle.data.DataMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

class BallerTargetMessage(intent: Intent, content: BallerTarget?, timeoutInMillis: Long? = 400) :
    DataMessage<BallerTarget?>(intent, content, timeoutInMillis)