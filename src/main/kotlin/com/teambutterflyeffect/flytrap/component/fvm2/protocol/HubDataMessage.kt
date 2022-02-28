package com.teambutterflyeffect.flytrap.component.fvm2.protocol

import com.teambutterflyeffect.flytrap.system.lifecycle.data.DataMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

class HubDataMessage(intent: Intent, content: HubEntity, timeoutInMillis: Long? = 1000) : DataMessage<HubEntity>(intent, content, timeoutInMillis = timeoutInMillis)