package com.teambutterflyeffect.flytrap.system.safety

import com.teambutterflyeffect.flytrap.system.error.ErrorCode
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

class KillSwitchMessage(intent: Intent, code: ErrorCode = ErrorCode.NONE, reason: String = "Kill switch activated with KillSwitchMessage") : ObjectMessage(intent)