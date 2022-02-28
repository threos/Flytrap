package com.teambutterflyeffect.flytrap.component.tower.message

import com.teambutterflyeffect.flytrap.component.tower.positioning.TowerTarget
import com.teambutterflyeffect.flytrap.system.lifecycle.data.DataMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

class TowerContentMessage(intent: Intent, content: List<TowerTarget>, timeoutInMillis: Long? = 1000) : DataMessage<List<TowerTarget>>(intent, content, timeoutInMillis)