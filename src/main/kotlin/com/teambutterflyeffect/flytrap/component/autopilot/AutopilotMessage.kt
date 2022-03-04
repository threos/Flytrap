package com.teambutterflyeffect.flytrap.component.autopilot

import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

class AutopilotMessage(intent: Intent, timeoutInMillis: Long? = 500) : ObjectMessage(intent, timeoutInMillis)