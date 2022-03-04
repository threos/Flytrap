package com.teambutterflyeffect.flytrap.component.intake.activator

import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

class IntakeActivatorMessage(intent: Intent, timeoutInMillis: Long? = 600) : ObjectMessage(intent, timeoutInMillis)