package com.teambutterflyeffect.flytrap.component.baller.light.animation

import com.teambutterflyeffect.flytrap.component.baller.light.BallerLightAnimator
import edu.wpi.first.wpilibj.AddressableLEDBuffer

class WarningAnimation(val red: Int, val green: Int, val blue: Int): BallerLightAnimator {
    override fun invoke(counter: Int, buffer: AddressableLEDBuffer) {
        val multiplier = if(counter.div(20) % 2 == 0) {
            1
        } else 0
        val r = red * multiplier
        val g = green * multiplier
        val b = blue * multiplier
        for(i in 0 until buffer.length) buffer.setRGB(i, r, g, b)
    }
}