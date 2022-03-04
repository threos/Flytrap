package com.teambutterflyeffect.flytrap.component.baller.finder

import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

class BallerFinderResultMessage(intent: Intent, timeoutInMillis: Long? = null) :
    ObjectMessage(intent, timeoutInMillis)