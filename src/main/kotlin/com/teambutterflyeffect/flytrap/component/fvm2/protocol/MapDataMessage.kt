package com.teambutterflyeffect.flytrap.component.fvm2.protocol

import com.teambutterflyeffect.flytrap.component.fvm2.protocol.VisionMapEntity
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

open class MapDataMessage(val entities: List<VisionMapEntity>, intent: Intent) : ObjectMessage(intent)