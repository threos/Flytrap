package com.teambutterflyeffect.flytrap.component.fvm2.protocol

import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

open class VisionDataMessage(val entities: List<VisionEntity>, intent: Intent) : ObjectMessage(intent) {
    override fun toString(): String {
        return "<VisionDataMessage entities=${entities.size}\n"+
                "intent=$intent>"
    }
}