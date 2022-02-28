package com.teambutterflyeffect.flytrap.component.driverassist.targetgravity.message

import com.teambutterflyeffect.flytrap.component.driverassist.targetgravity.GravityForce
import com.teambutterflyeffect.flytrap.system.lifecycle.data.DataMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

class GravityForceMessage(intent: Intent, content: GravityForce, timeoutInMillis: Long = 20) :
    DataMessage<GravityForce>(intent, content, timeoutInMillis)