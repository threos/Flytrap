package com.teambutterflyeffect.flytrap.component.baller.light

import com.teambutterflyeffect.flytrap.system.lifecycle.data.DataMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent
import edu.wpi.first.wpilibj.AddressableLEDBuffer

typealias BallerLightAnimator = (counter: Int, buffer: AddressableLEDBuffer) -> Unit

class BallerLightMessage(intent: Intent, data: BallerLightAnimator, timeoutInMillis: Long? = null) : DataMessage<BallerLightAnimator>(intent, data, timeoutInMillis)