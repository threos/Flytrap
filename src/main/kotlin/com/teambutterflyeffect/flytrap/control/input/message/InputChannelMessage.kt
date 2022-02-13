package com.teambutterflyeffect.flytrap.control.input.message

import com.teambutterflyeffect.flytrap.control.input.StaticInputChannel
import com.teambutterflyeffect.flytrap.system.lifecycle.data.ObjectMessage
import com.teambutterflyeffect.flytrap.system.lifecycle.objects.Intent

class InputChannelMessage(
    val port: Int,
    val channel: StaticInputChannel,
    intent: Intent,
) : ObjectMessage(intent, 200)