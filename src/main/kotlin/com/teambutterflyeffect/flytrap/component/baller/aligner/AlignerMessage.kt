package com.teambutterflyeffect.flytrap.component.baller.aligner

import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

class AlignerMessage(intent: Intent, timeoutInMillis: Long = 200) : ObjectMessage(intent, timeoutInMillis) {
}